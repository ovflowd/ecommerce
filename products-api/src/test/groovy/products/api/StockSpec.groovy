package products.api

import grails.test.mixin.TestFor
import spock.lang.*

@TestFor(Stock)
class StockSpec extends Specification {
    def 'test if a normal stock entry passes on the validation'() {
        when:
        def stock = new Stock(details: 'A real good stock entry.', amount: 200, productId: "54cc9bac798411e7b5a5be2e44b06b34").save()

        then:
        stock
    }

    def 'test if validation works with invalid amount.'() {
        // This test is ambiguous since as `amount` being an integer, Groovy does the conversion automatically.
        when:
        def stock = new Stock(amount: 20.33553535, productId: "54cc9bac798411e7b5a5be2e44b06b34").save()

        then:
        stock
    }

    def 'test if validation works with invalid productId pattern (UUID).'() {
        when:
        def stock = new Stock(amount: 20, productId: "an-invalid-uuid").save()

        then:
        !stock
    }
}