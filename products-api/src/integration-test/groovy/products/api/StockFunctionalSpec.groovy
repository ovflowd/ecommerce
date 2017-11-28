package products.api

import geb.spock.GebSpec
import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback

@Integration
@Rollback
class StockFunctionalSpec extends GebSpec {
    RestBuilder rest() {
        RestBuilder.newInstance()
    }

    void 'Test Listing Stocks'() {
        when: 'Accessing the Stock Listing'
            def resp = rest().get("$baseUrl/product/stock")
        then: 'We will receive an empty List'
            resp.text == '[]'
    }

    void 'Test Creating a new Stock'() {
        when: 'Creating a new Product and Stock Entry'
            def productResp = rest().post("$baseUrl/product") {
                contentType 'application/json'
                json ([name: 'A Happy Product', price: 20.20])
            }
            def stockResp = rest().post("$baseUrl/product/stock") {
                contentType 'application/json'
                json ([productId: productResp.json.id, details: 'Creating stock for Product', amount: 20])
            }
        then: 'We will receive a success status code'
            productResp.status == 200
            stockResp.status == 200
    }
}
