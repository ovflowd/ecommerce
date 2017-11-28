package purchase.api

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Item)
@Mock(Cart)
class ItemDomainSpec extends Specification {
    void 'test if the productId validation works'() {
        when:
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
            def item = new Item(productId: 'INVALID-ID', amount: 20, cartId: cart).save()
        then:
            !item
    }

    void 'test if cart constraint works works'() {
        when:
            Cart cart = null
            def item = new Item(productId: '8a8a81e85db13efe015db13f15750000', amount: 20, cartId: cart).save()
        then:
            !item
    }

    void 'test if the amount validation works'() {
        when:
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
            def item = new Item(productId: '8a8a81e85db13efe015db13f15750000', amount: 0, cartId: cart).save()
        then:
            !item
    }

    void 'test if a valid item stores with success'() {
        when:
            def cart = new Cart(customer: 'Someone Told', email: 'bad-ass@world.org', card: '4716930483418849').save()
            def item = new Item(productId: '8a8a81e85db13efe015db13f15750000', amount: 20, cartId: cart).save()
        then:
            item
    }
}
