package products.api

import grails.converters.JSON
import grails.rest.*

class ProductsController extends RestfulController {
	static responseFormats = ['json']

    /**
     * Index Method
     *
     * Used to List Products or Search for Products
     */
    def index() {
        respond Product.createCriteria().list(params) {
            if(params.name) { or { eq('name', params.name) } }
            if(params.price) { or { eq('price', params.price) } }
            if(params.stock) { or { eq('stock', params.stock) } }
        }
    }

    def save() {
        def product = Product.create()

        bindData product, request

        if(product.validate()) {
            product.save flush: true

            respond (message: 'Product Registered with Success', id: product.id) as JSON
        } else {
            response.status = 405

            respond (message: 'Invalid Input. Check your jSON.')
        }
    }

    ProductsController() {
        super(Product)
    }
}
