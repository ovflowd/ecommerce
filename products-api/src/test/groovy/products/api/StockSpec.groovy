package products.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.*

class StockSpec extends Specification implements DomainUnitTest<Stock> {
    void 'test if a normal stock entry passes on the validation'() {
        when:
            def stock = new Stock(details: 'A real good stock entry.', amount: 200, productId: "54cc9bac798411e7b5a5be2e44b06b34").save()
        then:
            stock
    }

    void 'test if validation works with invalid amount.'() {
        when:
            def stock = new Stock(amount: 20.33553535, productId: "54cc9bac798411e7b5a5be2e44b06b34").save()
        then:
            stock
    }

    void 'test if validation works with invalid productId pattern (UUID).'() {
        when:
            def stock = new Stock(amount: 20, productId: "an-invalid-uuid").save()
        then:
            !stock
    }
}