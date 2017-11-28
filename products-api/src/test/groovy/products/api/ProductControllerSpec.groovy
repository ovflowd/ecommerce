package products.api

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.test.annotation.Rollback
import spock.lang.*

@TestFor(ProductsController)
@Mock([Product])
@Rollback
class ProductControllerSpec extends Specification {
    void setupData() {
        Product.saveAll(new Product(name: 'A stocked Product', price: 2.20))
    }

    void 'test listing existent products'() {
        given:
            setupData()
        when:
            controller.params.id = Product.first().id
            controller.index()
        then:
            response.text != '[]'
    }

    void 'test listing non existent products'() {
        given:
            setupData()
        when:
            controller.params.name = 'My Little Pony 20 sticker Pack'
            controller.index()
        then:
            response.text == '[]'
    }

    void 'test creating product'() {
        when:
            request.method = 'POST'
            request.json = new Product(name: 'A stocked Product', price: 2.20) as JSON
            controller.save()
        then:
            response.status == 200
    }

    void 'test creating an invalid product'() {
        when:
            request.method = 'POST'
            request.json = new Product(name: 'A stocked Product', price: -20) as JSON
            controller.save()
        then:
            response.status == 405
    }

    void 'test updating a product (changing the name)'() {
        given:
            setupData()
        when:
            request.method = 'PUT'
            controller.params.id = Product.first().id
            request.json = new Product(name: 'A stocked Product', price: 4.00) as JSON
            controller.update()
        then:
            response.status == 200
    }

    void 'test updating a non existent product'() {
        given:
            setupData()
        when:
            request.method = 'PUT'
            controller.params.id = Math.random()
            request.json = new Product(name: 'A stocked Product', price: 4.00) as JSON
            controller.update()
        then:
            response.status == 404
    }

    void 'test removing an existent product'() {
        given:
            setupData()
        when:
            request.method = 'DELETE'
            controller.params.id = Product.first().id
            controller.delete()
        then:
            response.status == 200
    }

    void 'test removing a non existent product'() {
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