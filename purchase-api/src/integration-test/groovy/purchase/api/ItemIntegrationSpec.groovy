package purchase.api

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.*

@Integration
@Rollback
class ItemIntegrationSpec extends Specification {
    void setup() {
        Cart.findAll().each { it.delete() }
    }

    void 'test if registering data with normal behaviour works'() {
        when: 'A real normal cart item being registered...'
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
            new Item(productId: '8a8a81e85db13efe015db13f15750000', amount: 20, cartId: cart).save()
        then: 'We should succeed in registering it'
            cart
            Cart.count() == 1
            Item.count() == 1
    }
}
