package products.api

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.test.annotation.Rollback
import spock.lang.Specification

@TestFor(StockController)
@Mock([Stock, Product])
@Rollback
class StockControllerSpec extends Specification {
    void setupData() {
        Product.saveAll(new Product(name: 'A stocked Product', price: 2.20))
    }

    void 'test listing existent stocks'() {
        given:
            setupData()
        when:
            params.name = 'A stocked Product'
            controller.index()
        then:
            response.json != []
    }

    void 'test listing non existent products'() {
        given:
            setupData()
        when:
            params.name = 'My Little Pony 20 sticker Pack'
            controller.index()
        then:
            response.json == []
    }

    void 'test creating a stock entry'() {
        when:
            request.method = 'POST'
            request.json = [productId: Product.first().id, details: 'Updated Stock because of Cart Checkout', amount: 10] as JSON
            controller.save()
        then:
            response.status == 200
    }
}