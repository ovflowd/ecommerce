package products.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class LogSpec extends Specification implements DomainUnitTest<Log> {
    void 'test if a normal log passes on the validation'() {
        when:
            def log = new Log(relatedElement: 'some-element', relatedTable: 'product', details: 'Some shit happened here.').save()
        then:
            log
    }
}