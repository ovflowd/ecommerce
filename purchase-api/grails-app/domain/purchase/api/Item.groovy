package purchase.api

/**
 * Class Item
 *
 * A Cart Item it's a semantic reference to certain products
 *
 * @see https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/mapping.html#mapping-declaration-id-enhanced
 *  For Generators
 */
class Item {

    /* Identifiers */
    String id // Using UUID Generator of Hibernate

    /* Attributes */
    String productId // The Identifier of the Product
    Integer amount // The amount to Purchase

    /* Timestamps */
    Date addedAt = new Date() // When it was Added
    Date editedAt = new Date() // Last time Edited

    /* Social Relationships */
    static belongsTo = [cartId: Cart] // The Cart Identifier

    static constraints = {
        productId blank: false, nullable: false, matches: "[0-9A-Fa-f]+", size: 32..32
        amount notEqual: 0, validator: { return (it % 1) == 0 }
        addedAt blank: true, nullable: true
        editedAt blank: true, nullable: true
    }

    static mapping = {
        id generator: 'uuid'
        cartId column: 'cart_id'
    }
}
