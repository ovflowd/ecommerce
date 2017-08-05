package products.api

import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

/**
 * Stock Specification Test
 *
 * Just verifies if the Stock Entity has a normal behaviour
 */

@Integration
@Rollback
class StockSpec extends Specification {
    def setup() {
        Product.saveAll(new Product(name: 'A stocked Product', price: 2.20))
    }

    def cleanup() {}

    void 'test if the given amount can be zero'() {
        Product.saveAll(new Product(name: 'Test Product', description: 'A Product has no Description. GoT', price: 2.29))

        when: 'A given amount being zero'
        Stock stock = new Stock(amount: 0, details: 'An invalid stock entry', productId: Product.first().id)
        stock.save()

        then: 'We will found errors, because amount should be different from zero'
        stock.hasErrors()
        stock.errors.getFieldError('amount')
        Stock.count() == 0
    }

    void 'test if the default details are applied when not providing one'() {
        when: 'Not providing a details...'
        Stock stock = new Stock(amount: 2, productId: Product.first().id)
        stock.save()

        then: 'A default details need to appear, and the stock entry be stored'
        Stock.count() == 1
        Stock.first().details == 'Stock entry without description.'
    }
}
