package purchase.api

import grails.core.GrailsApplication
import grails.util.Environment
import grails.plugins.*
import grails.util.Holders
import groovy.transform.CompileStatic

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
