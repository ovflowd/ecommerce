package products.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

/**
 * Stock Controller Specification Test
 *
 * Just an white hat test case to check if the controller has a normal behaviour
 */
class StockControllerSpec extends Specification implements ControllerUnitTest<StockController> {
    def setup() {
        Product.saveAll(new Product(name: 'Test Product', description: 'A Product has no Description. GoT', price: 2.29))
    }

    void 'test if is possible to register a stock entry with an invalid product identifier'() {
        request.method = 'POST'

        when: 'We provide an invalid product identifier'
        request.json = '{"productId": "im-not-human","details": "Why does I exists?","amount": 2}'
        controller.save()

        then: 'We should find an Error response'
        response.status == 404
    }

    void 'test if is possible to register a stock entry without providing a productId'() {
        request.method = 'POST'

        when: 'We we don\'t provide a productId'
        request.json = '{"details": "Why does I exists?","amount": 2}'
        controller.save()

        then: 'We should find an Error response'
        response.status == 400
    }

    void 'test if is possible to register a stock entry with an empty json'() {
        request.method = 'POST'

        when: 'We we don\'t provide a productId'
        request.json = '{}'
        controller.save()

        then: 'We will find an Error response'
        response.status == 400
    }

    void 'test if register works if everything is provided correctly'() {
        request.method = 'POST'

        when: 'We provided everything correctly..'
        request.json = '{"productId": "' + Product.first().id + '","details": "Why does I exists?","amount": 2}'
        controller.save()

        then: 'We should have a Success response..'
        response.status == 200
        Stock.count() == 1
    }

    void 'test if the controller response when listing works correctly'() {
        request.method = 'GET'

        when: 'We want to list stock entries..'
        controller.index()

        then: 'We should have exactly one entry, since we registered one.'
        response.status == 200
        response.json.size() == 1
        response.json[0].productId == Product.first().id
    }

    void 'test if the related product amount has increased'() {
        request.method = 'POST'

        when: 'We create a stock entry that has -2 amount...'
        request.json = '{"productId": "' + Product.first().id + '","details": "Why does I exists?","amount": -2}'
        controller.save()

        then: 'Our related product amount should be zero'
        Product.first().stock == 0
    }
}