package products.api

import grails.util.Environment

/**
 * Class Bootstrap
 *
 * This class handles with business logic that happens on the startup of Grails
 */
class BootStrap {
    def init = { servletContext ->
        // If we are on the Development Environment, let's add some Default Data.
        if (Environment.current == Environment.DEVELOPMENT) {
            // We add some Products
            Product.saveAll(
                    new Product(name: 'Java for Dummies (For Dummies (Computers))', description: '6th ed. Edition - by Barry Burd PH.D.', price: 36.34),
                    new Product(name: 'Beginning Groovy and Grails: From Novice to Professional', description: '1st Edition - by Christopher M. Judd (Author), Joseph Faisal Nusairat', price: 38.21),
                    new Product(name: 'Linkedin Premium Subscription', description: 'A voucher for a single month Linkedin Career Premium Subscription', price: 15.6)
            )

            // We get some identifiers
            def javaIdentifier = Product.first().id, linkedinIdentifier = Product.last().id

            // We add single Stock entries for those Products
            Stock.saveAll(
                    new Stock(details: 'Lojas Americanas added Stock for Java For Dummies Book', amount: 20, productId: javaIdentifier),
                    new Stock(details: 'Linkedin has now 20000 more slots for Linkedin Premium Vouchers. Get yours now.', amount: 20000, productId: linkedinIdentifier)
            )
        }
    }

    def destroy = {}
}
