package products.api

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import spock.lang.*

@Integration
@Rollback
class ProductIntegrationSpec extends Specification {
    void 'test if registering data with normal behaviour works'() {
        when: 'A real normal product being registered...'
            new Product(name: 'A normal and sad product', description: 'We should not care with it.', price: 2.20).save(flush: true)
        then: 'We should succeed in registering it'
            Product.count() == 1
    }

    void 'test if the given price can be zero'() {
        when: 'A given price being zero'
            new Product(name: 'A sad product', description: 'We should not care with it.', price: 0).save(flush: true)
        then: 'We should have errors since the price cannot be zero.'
            Product.count() == 0
    }

    void 'test if the given price can be something that isn\'t a real price'() {
        when: 'A given price is not a float'
            new Product(name: 'A sad product', description: 'We should not care with it.', price: -2).save(flush: true)
        then: 'We should have errors since the price must be a float number'
            Product.count() == 0
    }
}
