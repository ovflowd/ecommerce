package purchase.api

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.grails.web.json.JSONObject

/**
 * Cart Controller
 *
 * Manages all operations that happens with a Cart including it Items
 *
 * @see https://jwt.io/introduction/ JWT Documentation
 */
@org.springframework.web.bind.annotation.RestController
class CartController extends RestController {
    static responseFormats = ['json'], allowedMethods = [index: "GET", create: "POST", include: "POST", checkout: "POST", delete: "DELETE", remove: "DELETE", removeById: "DELETE"]

    /**
     * Index Method
     *
     * Used to List all the Carts or search for Carts
     *
     * @return a List of Carts that matches the search criteria or all Carts
     */
    def index() {
        // Check if any of the criteria it's used on the query string
        JSON.use('deep')

        render Cart.createCriteria().list(params) {
            if (params.name) {
                or { eq('customer', params.name) }
            }
            if (params.email) {
                or { eq('email', params.email) }
            }
            if (params.id) {
                or { eq('id', params.id) }
            }
        } as JSON
    }

    /**
     * Create Method
     *
     * Used to Create a new Cart
     *
     * @return A successful message if the Cart was created with it Identifier, an error Message if something goes wrong
     */
    @Transactional
    def create() {
        def cart = Cart.create()

        bindData cart, request.JSON

        // Check if the Body has a valid Mapping
        if (!cart.validate()) {
            response.status = 405

            respond message: 'Invalid Input. Check your jSON.', 'error': cart.errors.fieldError.field

            return
        }

        // Set the Expire Time of the Token from the Given Configuration
        use(TimeCategory) {
            cart.expiresOn = config().purchaseApi.expireTime.seconds.from.now
        }

        cart.save flush: true

        // Generate the JWT Token
        def token = Jwts.builder().setId(cart.id).setExpiration(cart.expiresOn).setSubject(cart.customer).signWith(SignatureAlgorithm.HS256, jwtSignature()).compact()

        respond message: 'Cart Added with Success', id: cart.id, token: token
    }

    /**
     * Delete Method
     *
     * Used to Remove a Cart from the Database
     *
     * @return A successful message if the Cart was deleted with success, an Error message if the cart doesn't exists
     */
    @Transactional
    def delete() {
        // Check if the Id Parameter is in the Request Path
        if (!params.id) {
            response.status = 400

            respond message: 'Invalid Identifier or no Identifier supplied'

            return
        }

        def cart = Cart.get(params.id as Serializable)

        // Check if the Product exists with the provided Identifier
        if (!cart) {
            response.status = 404

            respond message: 'Cart not Found. Ensure that the Identifier it\'s correct.'

            return
        }

        cart.delete flush: true

        respond message: 'Cart Removed with Success.'
    }

    /**
     * Include Method
     *
     * Used to Add a new Item on our Cart
     *
     * @return A successful message if the Item was added with success, an error message if something goes wrong
     */
    @Transactional
    def include() {
        // Check if JWT is present and valid
        def identifier = jwt()

        if (!identifier)
            return

        // Uses the Identifier in order to get the CartId
        request.JSON.cartId = request.JSON.cartId ?: identifier

        def item = Item.create()

        bindData item, request.JSON

        // Check if the Body has a valid Mapping
        if (!item.validate()) {
            response.status = 405

            respond message: 'Invalid Input. Check your jSON.', 'errors': item.errors.fieldError.field

            return
        }

        def product = builder().get(productsApi().path('product').query('id={productId}').buildAndExpand([productId: request.JSON['productId'] as String]).toUriString())

        product.json instanceof JSONObject

        // Check if the Product really exists
        if (!product.json.any() || product.status == 404) {
            response.status = 404

            respond message: 'Product doesn\'t exists. Verify your productId.'

            return
        }

        // Get jSON content aka Product
        product = product.json[0]

        // Check if we have the necessary amount for it
        if (!product.stock || product.stock < request.JSON['amount']) {
            response.status = 405

            respond message: 'The Product has an insufficient amount. Please try order in a different amount.'

            return
        }

        item.save flush: true

        respond message: 'Item added to Cart with Success', id: item.id
    }

    /**
     * Remove Method
     *
     * Used to Remove a set of Cart Items from the Database
     *
     * @return A successful message if the Items were was deleted with success, an Error message if the cart doesn't exists
     */
    @Transactional
    def remove() {
        // Check if JWT is present and valid
        def identifier = jwt()

        if (!identifier)
            return

        // Uses the Identifier in order to get the CartId
        identifier = params.cartId ?: identifier

        // Check if all the required parameters are on request
        if (!params.productId || !params.amount) {
            response.status = 400

            respond message: 'Invalid Amount of Product Identifier given.'

            return
        }

        def item = Item.createCriteria().get() {
            eq('productId', params.productId)
            cartId {
                eq('id', identifier)
            }
        }

        // If the item doesn't exists we can't continued
        if (!item) {
            response.status = 404

            respond message: 'Either Cart Identifier or Product Identifier were not found.'

            return
        }

        // Ignore the fact that maybe the final amount can be negative..
        if ((item.properties.amount - params.amount.toInteger()) <= 0) {
            item.delete flush: true

            respond message: 'Item was deleted with success, since the amount were zeroed'

            return
        }

        item.properties.amount -= params.amount.toInteger()

        item.save flush: true

        respond message: 'Item amount on purchase reduced', newAmount: item.properties.amount
    }

    /**
     * RemoveById Method
     *
     * Used to Remove a specific Cart Item from the Database
     *
     * @return A successful message if the Item was deleted with success, an Error message if the cart doesn't exists
     */
    @Transactional
    def removeById() {
        // Check if JWT is present and valid
        if (!jwt())
            return

        // Check if the Id Parameter is in the Request Path
        if (!params.id) {
            response.status = 400

            respond message: 'Invalid Identifier or no Identifier supplied'

            return
        }

        def item = Item.get(params.id as Serializable)

        // Check if the Item exists with the provided Identifier
        if (!item) {
            response.status = 404

            respond message: 'Item not Found. Ensure that the Identifier it\'s correct.'

            return
        }

        item.delete flush: true

        respond message: 'Item Removed with Success.'
    }

    /**
     * Checkout Method
     *
     * Used to Finish a Purchase and Checkout the Purchase
     *
     * @return A successful message including the items that you bought if the Checkout happens right, an Error message otherwise
     */
    @Transactional
    def checkout() {
        // Check if JWT is present and valid
        def identifier = jwt()

        if (!identifier)
            return

        // Uses the Identifier in order to get the CartId
        identifier = request.JSON['cartId'] ?: identifier

        // Check if the jSON contains the cart Identifier
        if (!identifier) {
            response.status = 400

            respond message: 'Invalid Identifier or no Identifier supplied'

            return
        }

        def cart = Cart.get(identifier as Serializable)

        // Check if the Cart exists
        if (!cart) {
            response.status = 404

            respond message: 'Cart not Found. Ensure that the Identifier it\'s correct.'

            return
        }

        def finalPrice = 0F

        // Iterate through all Product Items and do some validations
        // Here happens some really bad workarounds (made because I hadn't time)
        cart.items.removeAll { ite ->
            // Get current Product Item
            def product = builder().get(productsApi().path('product').query('id={productId}').buildAndExpand([productId: ite.productId]).toUriString())

            // Check if it still exists, if not sadly removing this product.
            if (!product.json.any())
                return true

            // Get First Element
            product = product.json[0]

            // Check if amount it's valid, if not sadly removing this product.
            if (!product.stock || product.stock < ite.amount)
                return true

            if (!product.price || product.price <= 0)
                return true

            finalPrice += (product.price * ite.amount).toFloat()

            builder().post(productsApi().path('product/stock').build().toUriString()) {
                contentType 'application/json'
                json([productId: ite.productId, details: cart.customer + ' is ordering it.', amount: -ite.amount])
            }

            return false
        }

        def items = []

        // Sorry I didn't found another way to do it. If it exists, I will be glad of knowing about it.
        cart.items.toArray().each {
            it -> items.add([productId: it.productId, amount: it.amount])
        }

        cart.delete flush: true

        respond message: cart.customer + ', thanks for ordering with us.', boughtItems: items, boughtWith: cart.card, finalPrice: 'USD: ' + finalPrice
    }

    /**
     * Jwt Method
     *
     * Does the JWT Authorization Validation when Required
     *
     * @return the Identifier if the JWT is valid and not expired, false otherwise
     */
    def jwt() {
        def authorization = request.getHeader('authorization')

        // Check if authorization header is present
        if (!authorization) {
            response.status = 403

            respond message: 'You didn\'t provided a Token'

            return false
        }

        try {
            // Tries to decode the JWT token
            Claims claims = Jwts.parser().setSigningKey(jwtSignature()).parseClaimsJws(authorization).getBody() as Claims

            // Check if the Token hasn't expired
            if (claims.getExpiration() < new Date()) {
                response.status = 403

                respond message: 'Your Token has expired'

                return false
            }

            return claims.getId()
        } catch (Exception ignored) { // We have an invalid JWT here!
            response.status = 403

            respond message: 'Invalid given JWT Token.'

            return false
        }
    }
}
