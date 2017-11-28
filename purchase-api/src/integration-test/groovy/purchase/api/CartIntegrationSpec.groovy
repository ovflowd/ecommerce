package purchase.api

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.*

@Integration
@Rollback
class CartIntegrationSpec extends Specification {
    void 'test if registering data with normal behaviour works'() {
        when: 'A real normal cart being registered...'
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
        then: 'We should succeed in registering it'
            cart
            Cart.count() == 1
    }
}
