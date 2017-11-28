package products.api

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Log)
class LogSpec extends Specification {
    void 'test if a normal log passes on the validation'() {
        when:
            def log = new Log(relatedElement: 'some-element', relatedTable: 'product', details: 'Some shit happened here.').save()
        then:
            log
    }
}