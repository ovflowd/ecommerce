package purchase.api

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
            // We create one Cart
            def cart = new Cart(customer: 'Andrios Robert', email: 'andrios.robert@pismo.io', card: '4716930483418849').save()
            // And add a Item on it Basket
            new Item(productId: '1619de8c5be14a5f9331bf1668faed7a', cartId: cart, amount: 30).save()
        }
    }

    def destroy = {}
}
