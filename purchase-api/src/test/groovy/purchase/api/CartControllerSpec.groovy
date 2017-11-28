package purchase.api

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.test.annotation.Rollback
import spock.lang.*

@TestFor(CartController)
@Mock([Item])
@Rollback
class CartControllerSpec extends Specification {
    void setupData() {
        Cart.saveAll(new Cart(customer: 'Bad Ass', email: 'valid@email.org', card: 'im-not-giving-my-card-to-you'))
    }

    void 'test listing existent carts'() {
        given:
            setupData()
        when:
            controller.params.id = Cart.first().id
            controller.index()
        then:
            response.text != '[]'
    }

    void 'test creating cart'() {
        when:
            request.method = 'POST'
            request.json = new Cart(customer: 'Bad Ass', email: 'valid@email.org', card: 'im-not-giving-my-card-to-you')
            controller.create()
        then:
            response.status == 200
    }

    void 'test creating an invalid cart cart'() {
        when:
            request.method = 'POST'
            request.json = new Cart(email: 'valid@email.org')
            controller.create()
        then:
            response.status == 405
    }

    void 'test removing an existent cart'() {
        given:
            setupData()
        when:
            request.method = 'DELETE'
            controller.params.id = Cart.first().id
            controller.delete()
        then:
            response.status == 200
    }

    void 'test removing a non existent cart'() {
        given:
            setupData()
        when:
            request.method = 'DELETE'
            controller.params.id = Math.random()
            controller.delete()
        then:
            response.status == 404
    }
}