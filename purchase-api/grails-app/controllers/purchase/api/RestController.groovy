package purchase.api

import grails.plugins.rest.client.RestBuilder
import org.springframework.web.util.UriComponentsBuilder

abstract class RestController {

    RestBuilder builder
    UriComponentsBuilder productsApi
    String jwtSignature

    RestController() {
        // Set the productAPI URI Builder
        productsApi = UriComponentsBuilder.newInstance().scheme(grailsApplication.config.productsApi.protocol as String)
                .host(grailsApplication.config.productsApi.hostname as String).port(grailsApplication.config.productsApi.port as Integer)
        // Generate the JWT Signature
        jwtSignature = grailsApplication.config.purchaseApi.jwtHash as String
        // Instantiate the RestBuilder
        builder = new RestBuilder()
    }
}
