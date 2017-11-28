package purchase.api

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.test.annotation.Rollback
import spock.lang.*

@TestFor(CartController)
@Mock([Item])
@Rollback
class CartControllerSpec extends Specification {

}