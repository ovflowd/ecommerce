package products.api

import org.springframework.validation.BindingResult

/**
 * Products Controller
 *
 * Manages all operations that happens with Products
 */
class ProductsController {
    static responseFormats = ['json']

    /**
     * Index Method
     *
     * Used to List Products or Search for Products
     *
     * @return a List of Products matching the search criteria or all Products if no criteria used
     */
    def index() {
        respond(wtf: 'hello')

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
    def save() {
        def product = Product.create()

        bindData product, request.JSON

        if (product.validate()) {
            product.save flush: true

            respond(message: 'Product Registered with Success', id: product.id)
        } else {
            response.status = 405

            respond(message: 'Invalid Input. Check your jSON.')
        }
    }

    /**
     * Update Method
     *
     * Used to update constraints (properties) upon a specific Product
     *
     * @return Return severla messages as described on Swagger,
     *  but If any of the validations fail, data won't be changed.
     */
    def update() {
        if (params.id) {
            def product = Product.get(params.id as Serializable)

            if (product) {
                product.properties = request as BindingResult

                if (!product.hasErrors()) {
                    product.save flush: true

                    respond(message: 'Product Updated with Success')
                } else {
                    transactionStatus.setRollbackOnly()

                    respond(message: 'Invalid Input. Check your jSON')
                }
            } else {
                respond(message: 'Product not Found. Ensure that the Identifier it\'s correct.')
            }
        } else {
            respond(message: 'Invalid Identifier or no Identifier supplied')
        }
    }

    /**
     * Delete Method
     *
     * Remove a Product from the System
     *
     * @return If the Product exists, remove it, if not respond a Not Found message
     */
    def delete() {
        if (params.id) {
            def product = Product.get(params.id as Serializable)

            if (product) {
                product.delete flush: true

                respond(message: 'Product Removed with Success.')
            } else {
                respond(message: 'Product not Found. Ensure that the Identifier it\'s correct.')
            }
        } else {
            respond(message: 'Invalid Identifier or no Identifier supplied')
        }
    }
}
