package products.api

import grails.rest.*

class ProductsController extends RestfulController {
	static responseFormats = ['json']

    /**
     * Index Method
     *
     * Used to List Products or Search for Products
     */
    def index() {
        respond Product.where { name == params.name || price == params.price || stock == params.stock}.list()
    }

    ProductsController() {
        super(Product)
    }
}
