package purchase.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CartSpec extends Specification implements DomainUnitTest<Cart> {
    void 'test if the credit card validation works'() {
        when:
            def cart = new Cart(customer: 'Bad Ass', email: 'valid@email.org', card: 'im-not-giving-my-card-to-you').save()
        then:
            !cart
    }

    void 'test if the email validation works'() {
        when:
            def cart = new Cart(customer: 'Bad Ass', email: 'invalid-email', card: '4716930483418849').save()
        then:
            !cart
    }

    void 'test if the full name validation works'() {
        when:
            def cart = new Cart(customer: 'BadAss', email: 'bad-ass@world.org', card: '4716930483418849').save()
        then:
            !cart
    }

    void 'test if with everything right, everything goes right'() {
        when:
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
        then:
            cart
    }
}
