package products.api

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Product)
class ProductSpec extends Specification {
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