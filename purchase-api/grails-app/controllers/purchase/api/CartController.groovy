package purchase.api

import grails.transaction.Transactional

/**
 * Cart Controller
 *
 * Manages all operations that happens with a Cart including it Items
 */
class CartController {
    static responseFormats = ['json'],
           allowedMethods = [index: "GET", create: "POST", include: "POST", checkout: "POST", delete: "DELETE", remove: "DELETE"]

    /**
     * Index Method
     *
     * Used to List all the Carts or search for Carts
     *
     * @return a List of Carts that matches the search criteria or all Carts
     */
    def index() {
        // Check if any of the criteria it's used on the query string
        respond Cart.createCriteria().list(params) {
            if (params.name) {
                or { eq('customer', params.name) }
            }
            if (params.email) {
                or { eq('email', params.email) }
            }
            if (params.id) {
                or { eq('id', params.id) }
            }
        }
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

            respond(message: 'Invalid Input. Check your jSON.')

            return
        }

        cart.save flush: true

        respond(message: 'Cart Added with Success', id: cart.id, token: "Not Implemented Yet")
    }

    /**
     * Delete Method
     *
     * Used to Remove a Cart from the Database
     *
     * @return A successful message if the Cart was deleted with success, an Error message if the cart doesn't exists
     */
    def delete() {
        // Check if the Id Parameter is in the Request Path
        if (!params.id) {
            response.status = 400

            respond(message: 'Invalid Identifier or no Identifier supplied')

            return
        }

        //@TODO: Check if Token Expired

        def cart = Cart.get(params.id as Serializable)

        // Check if the Product exists with the provided Identifier
        if (!cart) {
            response.status = 404

            respond(message: 'Cart not Found. Ensure that the Identifier it\'s correct.')

            return
        }

        cart.delete flush: true

        respond(message: 'Cart Removed with Success.')
    }

    /**
     * Include Method
     *
     * Used to Add a new Item on our Cart
     *
     * @return A successful message if the Item was added with success, an error message if something goes wrong
     */
    def include() {
        def item = Item.create()

        bindData item, request.JSON

        // Check if the Body has a valid Mapping
        if (!item.validate()) {
            response.status = 405

            respond(message: 'Invalid Input. Check your jSON.')

            return
        }

        //@TODO Check if Product Identifier Exists
        //@TODO Check if Token Expired

        item.save flush: true

        respond(message: 'Item added to Cart with Success', id: item.id)
    }

    /**
     * Remove Method
     *
     * Used to Remove a set of Cart Items from the Database
     *
     * @return A successful message if the Items were was deleted with success, an Error message if the cart doesn't exists
     */
    def remove() {
        // Check if all the required parameters are on request
        if (!params.productId || !params.amount || !params.cartId) {
            response.status = 400

            respond(message: 'Invalid Card Identifier or Amount of Product Identifier given.')

            return
        }

        def item = Item.findWhere(productId: params.productId, cartId: params.cartId)

        // If the item doesn't exists we can't continued
        if (!item) {
            response.status = 404

            respond(message: 'Either Card Identifier or Product Identifier were not found.')

            return
        }

        // Ignore the fact that maybe the final amount can be negative..
        if ((item.properties.amount - params.amount) <= 0) {
            item.delete flush: true

            respond(message: 'Item was deleted with success, since the amount were zeroed')

            return
        }

        item.properties.amount -= params.amount

        item.save flush: true

        respond(message: 'Item amount on purchase reduced', newAmount: item.properties.amount)
    }

    /**
     * RemoveById Method
     *
     * Used to Remove a specific Cart Item from the Database
     *
     * @return A successful message if the Item was deleted with success, an Error message if the cart doesn't exists
     */
    def removeById() {
        // Check if the Id Parameter is in the Request Path
        if (!params.id) {
            response.status = 400

            respond(message: 'Invalid Identifier or no Identifier supplied')

            return
        }

        def item = Item.get(params.id as Serializable)

        // Check if the Item exists with the provided Identifier
        if (!item) {
            response.status = 404

            respond(message: 'Item not Found. Ensure that the Identifier it\'s correct.')

            return
        }

        item.delete flush: true

        respond(message: 'Item Removed with Success.')
    }

    /**
     * Checkout Method
     *
     * Used to Finish a Purchase and Checkout the Purchase
     *
     * @return A successful message including the items that you bought if the Checkout happens right, an Error message otherwise
     */
    def checkout() {
        //@TODO Implement Communication with Products API

        // Check if the jSON contains the cart Identifier
        if (!request.JSON.cartId) {
            response.status = 400

            respond(message: 'Invalid Identifier or no Identifier supplied')

            return
        }

        //@TODO Check if the Token Expired

        def cart = Cart.get(request.JSON.cartId as Serializable)

        // Check if the Cart exists
        if (!cart) {
            response.status = 404

            respond(message: 'Cart not Found. Ensure that the Identifier it\'s correct.')

            return
        }

        //@TODO Proceed the Communication with Products API removing the Items

        respond(message: cart.properties.customer + ', thanks for ordering with us.', boughtItems: cart.properties.items, boughtWith: cart.properties.card)

        cart.delete flush: true
    }
}
