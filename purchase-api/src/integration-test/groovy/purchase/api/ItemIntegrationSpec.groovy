package purchase.api

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class ItemIntegrationSpec extends Specification {
    void 'test if registering data with normal behaviour works'() {
        when: 'A real normal cart item being registered...'
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
            def item = new Item(productId: '8a8a81e85db13efe015db13f15750000', amount: 20, cartId: cart).save()
        then: 'We should succeed in registering it'
            item
            cart
            Cart.count() == 1
            Item.count() == 1
    }
}
