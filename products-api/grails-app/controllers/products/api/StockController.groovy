package products.api

class StockController {
    static responseFormats = ['json'],
           allowedMethods = [save: "POST"]

    /**
     * Save Method
     *
     * Used to store a new Stock Entry related to a Product
     *
     * @return A success message if everything occurs correctly, an error message
     *  if an Invalid Input is given.
     */
    def save() {
        if(request.JSON.productId) {
            def product = Product.get(request.JSON.productId as Serializable)

            if(product) {
                def stock = Stock.create()

                bindData stock, request.JSON

                if (stock.validate()) {
                    stock.save flush: true

                    product.properties.stock = Stock.findAllWhere(productId: product.id).amount.sum()

                    product.save flush: true

                    respond(message: 'Stock Entry registered with Success', id: stock.id)
                } else {
                    response.status = 405

                    respond(message: 'Invalid Input. Check your jSON.')
                }
            } else {
                respond(message: 'Product not Found. Ensure that the Identifier it\'s correct.')
            }
        } else {
            respond(mesage: 'Invalid Identifier or no Identifier supplied')
        }
    }

    /**
     * Index Method
     *
     * Used to List Products or Search for Products
     *
     * @return a List of Products matching the search criteria or all Products if no criteria used
     */
    def index() {
        respond Stock.createCriteria().list(params) {
            if (params.name) {
                or { eq('productId', Product.where {name: params.name}.first().id) }
            }
            if (params.productId) {
                or { eq('id', params.productId) }
            }
        }
    }
}
