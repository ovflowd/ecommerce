package products.api

import grails.test.mixin.integration.Integration
import grails.transaction.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class ProductFunctionalSpec extends GebSpec {
    RestBuilder rest() {
        RestBuilder.newInstance()
    }

    void 'Test Listing Products'() {
        when: 'Accessing the Product Listing'
            def resp = rest().get("$baseUrl/product")
        then: 'We will receive an empty List'
            resp.text == '[]'
    }

    void 'Test Creating a new Product'() {
        when: 'Creating a new Product'
            def resp = rest().post("$baseUrl/product") {
                contentType 'application/json'
                json ([name: 'A Happy Product', price: 20.2])
            }
        then: 'We will receive a success status code'
            resp.status == 200
    }

    void 'Test Removing a Product'() {
        when: 'Deleting a new Product'
            def resp = rest().delete("$baseUrl/product?id={productId}", [productId: Product.first().id])
        then: 'We will receive a success status code'
        resp.status == 200
    }
}
