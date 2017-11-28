package products.api

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.*

@Integration
@Rollback
class StockIntegrationSpec extends Specification {
    void setup() {
        Product.findAll().each { it.delete() }
        Stock.findAll().each { it.delete() }
    }

    void setupData() {
        Product.saveAll(new Product(name: 'A stocked Product', price: 2.20))
    }

    void 'test if the given amount can be zero'() {
        given:
            setupData()
        when: 'A given amount being zero'
            new Stock(amount: 0, details: 'An invalid stock entry', productId: Product.first().id).save(flush: true)
        then: 'We will found errors, because amount should be different from zero'
            Stock.count() == 0
    }

    void 'test if the default details are applied when not providing one'() {
        given:
            setupData()
        when: 'Not providing a details...'
            new Stock(amount: 2, productId: Product.first().id).save(flush: true)
        then: 'A default details need to appear, and the stock entry be stored'
            Stock.count() == 1
            Stock.first().details == 'Stock entry without description.'
    }
}
