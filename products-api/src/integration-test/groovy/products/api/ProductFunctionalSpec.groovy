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
                json ([name: 'A Happy Product', price: 20.20])
            }
        then: 'We will receive a success status code'
            resp.status == 200
    }

    void 'Test Removing a Product'() {
        when: 'Creating a Product and Deleting a new Product'
            def createRes = rest().post("$baseUrl/product") {
                contentType 'application/json'
                json([name: 'A Happy Product', price: 20.20])
            }
            def deleteResp = rest().delete("$baseUrl/product/{productId}") {
                urlVariables productId: createRes.json.id
            }
        then: 'We will receive a success status code'
            createRes.status == 200
            deleteResp.status == 200
    }
}
