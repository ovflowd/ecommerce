package products.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

/**
 * Product Controller Specification Test
 *
 * Just an white hat test case to check if the controller has a normal behaviour
 */
class ProductsControllerSpec extends Specification implements ControllerUnitTest<ProductsController> {
    void 'test if is possible register a product with a zeroed price'() {
        request.method = 'POST'

        when: 'We provide a zeroed price'
        request.json = '{"name": "A really good product.","description": "Made for you. Totally free.","price": 0}'
        controller.save()

        then: 'We should find an error message (because in real world, nothing is free)'
        response.status == 405
    }

    void 'test if registering normal data would register a product.'() {
        request.method = 'POST'

        when: 'We provide a zeroed price'
        request.json = '{"name": "A really good product.","description": "Made for you. Keep it Real","price": 2.20}'
        controller.save()

        then: 'We should have a successfully register'
        response.status == 200
        Product.count() == 1
    }

    void 'test if the controller response when listing works correctly'() {
        request.method = 'GET'

        when: 'We want to list products entries..'
        controller.index()

        then: 'We should have exactly one entry, since we registered one.'
        response.status == 200
        response.json.size() == 1
        response.json[0].id == Product.first().id
    }

    void 'test if the controller updates successfully an product'() {
        request.method = 'PUT'
        params.id = Product.first().id

        when: 'We want to update a Product. (The price it\'s really high now! aka Inflation)'
        request.json = '{"name": "A really good product.","description": "Made for you. Keep it Real","price": 100.00}'
        controller.update()

        then: 'We should have exactly one entry, since we registered one.'
        response.status == 200
        Product.first().price == 100
    }

    void 'test if the controller removes a product correctly'() {
        request.method = 'DELETED'
        params.id = Product.first().id

        when: 'We want to remove a product'
        controller.delete()

        then: 'We should now have zero products.'
        response.status == 200
        Product.count() == 0
    }
}