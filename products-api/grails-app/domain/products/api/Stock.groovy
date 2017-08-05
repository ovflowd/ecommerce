package products.api

/**
 * Class Stock
 *
 * A stock entry it's a historical state of a Product.
 * The amount in stock of a product it's considered as the sum of the Stock Entries.
 *
 * @observation The Business Logic will not allow that the stock amount becomes negative.
 * @observation a Stock entry can have negative or positive entries that means checkout
 *  or entrance of products.
 *
 * @see https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/mapping.html#mapping-declaration-id-enhanced
 *  For Generators
 */

class Stock {

    /* Identifiers */
    String id // Using UUID Generator of Hibernate

    /* Attributes */
    String details = "Stock entry without description." // Details about this Stock Entry
    Integer amount // Amount added or removed in the stock
    String productId // Product Identifier

    /* Timestamps */
    Date addedAt = new Date() // When it was Added
    Date editedAt = new Date() // Last time Edited

    static constraints = {
        details blank: true, nullable: false
        addedAt blank: true, nullable: true
        editedAt blank: true, nullable: true
        productId blank: false, nullable: false, matches: "[0-9A-Fa-f]+", size: 32..32
        amount notEqual: 0, validator: { return (it % 1) == 0 }
    }

    static mapping = {
        amount defaultValue: 1
        id generator: 'uuid'
    }
}
