package purchase.api

import grails.config.Config
import grails.plugins.rest.client.RestBuilder
import org.springframework.web.util.UriComponentsBuilder

abstract class RestController {
    // Get an Instance of RestBuilder
    RestBuilder builder() {
        RestBuilder.newInstance()
    }

    // Get actual Grails Application Config
    Config config() {
        grailsApplication.config
    }

    // Get Products API URI Builder
    UriComponentsBuilder productsApi() {
        UriComponentsBuilder.newInstance().scheme(config().productsApi.protocol as String)
                .host(config().productsApi.hostname as String).port(config().productsApi.port as Integer)
    }

    // Get JWT Signature Builder
    String jwtSignature() {
        grailsApplication.config.purchaseApi.jwtHash as String
    }
}
