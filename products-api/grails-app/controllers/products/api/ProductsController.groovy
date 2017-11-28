package products.api

import grails.transaction.Transactional
import org.springframework.validation.BindingResult

/**
 * Products Controller
 *
 * Manages all operations that happens with Products
 */
class ProductsController {
    static responseFormats = ['json'], allowedMethods = [index: "GET", save: "POST", update: "PUT"]

    /**
     * Index Method
     *
     * Used to List Products or Search for Products
     *
     * @return a List of Products matching the search criteria or all Products if no criteria used
     */
    def index() {
        // Check if any of the criteria it's used on the query string
        respond Product.createCriteria().list(params) {
            if (params.name) {
                or { eq('name', params.name) }
            }
            if (params.price) {
                or { eq('price', params.price) }
            }
            if (params.stock) {
                or { eq('stock', params.stock) }
            }
            if (params.id) {
                or { eq('id', params.id) }
            }
        }
    }

    /**
     * Save Method
     *
     * Used to store a new Product, the validation happens on the GORM.
     *
     * @return A success message if everything occurs correctly, an error message
     *  if an Invalid Input is given.
     */
    @Transactional
    def save() {
        def product = Product.create()

        // Check if either price property exists and is a valid float
        if (!request.JSON['price'] || (request.JSON['price'] % 1) == 0) {
            response.status = 405

            respond message: 'Invalid Input. Price isn\'t a valid float number.'

            return
        }

        request.JSON['stock'] = 0

        bindData product, request.JSON

        // Check if the Body has a valid Mapping
        if (!product.validate()) {
            response.status = 405

            respond message: 'Invalid Input. Check your jSON.', 'error': product.errors.fieldError.field

            return
        }

        product.save flush: true

        new Log(relatedTable: 'product', relatedElement: product.id, details: 'A new product was registered with name: ' + product.name + ', price: ' + product.price).save()

        respond message: 'Product Registered with Success', id: product.id
    }

    /**
     * Update Method
     *
     * Used to update constraints (properties) upon a specific Product
     *
     * @return Return severla messages as described on Swagger,
     *  but If any of the validations fail, data won't be changed.
     */
    @Transactional
    def update() {
        // Check if the Id Parameter is on the Request Path
        if (!params.id) {
            response.status = 400

            respond message: 'Invalid Identifier or no Identifier supplied'

            return
        }

        def product = Product.get(params.id as String)

        // Check if the Product Exists
        if (!product) {
            response.status = 404

            respond message: 'Product not Found. Ensure that the Identifier it\'s correct.'

            return
        }

        product.properties = request as BindingResult

        // Check if the Update Triggered Any Error
        if (product.hasErrors()) {
            response.status = 405

            respond message: 'Invalid Input. Check your jSON', 'error': product.errors.fieldError.field
        }

        new Log(relatedTable: 'product', relatedElement: product.id, details: 'A product was updated. new name: ' + product.name + ', new price: ' + product.price).save()

        product.properties.editedAt = new Date()

        product.save flush: true

        respond message: 'Product Updated with Success'
    }

    /**
     * Delete Method
     *
     * Remove a Product from the System
     *
     * @return If the Product exists, remove it, if not respond a Not Found message
     */
    @Transactional
    def delete() {
        // Check if the Id Parameter is in the Request Path
        if (!params.id) {
            response.status = 400

            respond message: 'Invalid Identifier or no Identifier supplied'

            return
        }

        def product = Product.get(params.id as String)

        // Check if the Product exists with the provided Identifier
        if (!product) {
            response.status = 404

            respond message: 'Product not Found. Ensure that the Identifier it\'s correct.'

            return
        }

        new Log(relatedTable: 'product', relatedElement: product.id, details: 'A product was deleted with name: ' + product.name).save()

        product.delete flush: true

        respond message: 'Product Removed with Success.'
    }
}
