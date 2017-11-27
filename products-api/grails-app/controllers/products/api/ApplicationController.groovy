package products.api

import grails.core.GrailsApplication
import grails.plugins.*
import groovy.transform.CompileStatic

@CompileStatic
class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
