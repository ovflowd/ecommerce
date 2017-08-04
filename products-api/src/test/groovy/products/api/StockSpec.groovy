package products.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

/**
 * Stock Specification Test
 *
 * Just verifies if the Stock Entity has a normal behaviour
 */
class StockSpec extends Specification implements DomainUnitTest<Stock> {
    def setup() {
        Product.saveAll(new Product(name: 'Test Product', description: 'A Product has no Description. GoT', price: 2.29))
    }

    void 'test if the given amount can be zero'() {
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
        Stock.first().details == 'Stock Entry without Description.'
    }

    void 'test if the default amount is applied when not providing one'() {
        when: 'Not providing an amount...'
        Stock stock = new Stock(productId: Product.first().id)
        stock.save()

        then: 'A default amount need to appear, and the stock entry be stored'
        Stock.count() == 2
        Stock.last().amount == 1
    }
}
