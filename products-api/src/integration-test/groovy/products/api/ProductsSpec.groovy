package products.api

import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class ProductsSpec extends Specification {
    def setup() {}

    def cleanup() {}

    void 'test if registering data with normal behaviour works'() {
        when: 'A real normal product being registered...'
        Product product = new Product(name: 'A normal and sad product', description: 'We should not care with it.', price: 2.20)
        product.save()

        then: 'We should succedd in registering it'
        !product.hasErrors()
        Product.count() == 1
    }

    void 'test if the given price can be zero'() {
        when: 'A given price being zero'
        Product product = new Product(name: 'A sad product', description: 'We should not care with it.', price: 0)
        product.save()

        then: 'We should have errors since the price cannot be zero.'
        product.hasErrors()
        product.errors.getFieldError('price')
        Product.count() == 0
    }

    void 'test if the given price can be something that isn\'t a real price'() {
        when: 'A given price is not a float'
        Product product = new Product(name: 'A sad product', description: 'We should not care with it.', price: -2)
        product.save()

        then: 'We should have errors since the price must be a float number'
        product.hasErrors()
        product.errors.getFieldError('price')
        Product.count() == 0
    }
}
