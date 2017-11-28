package products.api

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.test.annotation.Rollback
import spock.lang.Specification

@TestFor(StockController)
@Mock([Stock, Product, Log])
@Rollback
class StockControllerSpec extends Specification {
    void setupData() {
        Product.saveAll(new Product(name: 'A stocked Product', price: 2.20))
        Product.first().id = UUID.randomUUID().toString().replaceAll('-', '') // A little bit Hacky
        Product.first().save()
        Stock.saveAll(new Stock(productId: Product.first().id, details: 'A beautiful stock', amount: 20))
    }

    void 'test listing existent stocks'() {
        given:
            setupData()
        when:
            controller.params.name = 'A stocked Product'
            controller.index()
        then:
            response.text != '[]'
    }

    void 'test creating a stock entry'() {
        given:
            setupData()
        when:
            request.method = 'POST'
            request.json = new Stock(productId: Product.first().id, details: 'Added 20 amount for stock', amount: 20) as JSON
            controller.save()
        then:
            response.status == 200
    }

    void 'test updating stock'() {
        given:
            setupData()
        when:
            request.method = 'POST'
            request.json = new Stock(productId: Product.first().id, details: 'Deleted 20 amount of stock', amount: -20) as JSON
            controller.save()
        then:
            response.status == 200
    }

    void 'test updating stock to negative value'() {
        given:
            setupData()
        when:
            request.method = 'POST'
            request.json = new Stock(productId: Product.first().id, details: 'Deleted 20 amount of stock', amount: -40) as JSON
            controller.save()
        then:
            response.status == 405
    }
}