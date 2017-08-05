package purchase.api

/**
 * Class Cart
 *
 * A Cart it's a specie of Basket where you put Items that you want to buy.
 *
 * @see https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/mapping.html#mapping-declaration-id-enhanced
 *  For Generators
 */
class Cart {

    /* Identifiers */
    String id // Using UUID Generator of Hibernate

    /* Attributes */
    String customer // The customer name, he need to identify himself (at least the name)
    String email = "anonymous@world.org" // The customer name, he can also don't identify himself
    String card // We need a Credit Card, he need pay somehow.

    /* Timestamps */
    Date addedAt = new Date() // When it was Added
    Date editedAt = new Date() // Last time Edited
    Date expiresOn = new Date() // When it expires

    /* Social Relationships */
    static hasMany = [items: Item] // The Items inside the basket

    static constraints = {
        customer blank: false, nullable: false, matches: "(.*)\\s(.*)" // We don't accept special Unicode chars here.
        email email: true
        card creditCard: true, display: false
        addedAt blank: true, nullable: true, display: false
        editedAt blank: true, nullable: true, display: false
        expiresOn blank: true, nullable: true
    }

    static mapping = {
        id generator: 'uuid'
    }
}
