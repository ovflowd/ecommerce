package products.api

/**
 * Commerce Class
 *
 * Handles all other non derivative pages
 */
class CommerceController {
	static responseFormats = ['json']
	
    def index() {
        respond(message: 'Welcome to eCommerce Products API.')
    }
}
