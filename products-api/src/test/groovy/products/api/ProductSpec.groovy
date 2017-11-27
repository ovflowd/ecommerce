package products.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductSpec extends Specification implements DomainUnitTest<Product> {
    void 'test if a normal product passes on the validation'() {
        when:
            def product = new Product(name: 'A normal sad product.', price: 2.00).save()
        then:
            product
    }

    void 'test if validation works when the product is invalid (price)'() {
        when:
            def product = new Product(price: -20, name: 'An abnormal product.').save()
        then:
            !product
    }
}