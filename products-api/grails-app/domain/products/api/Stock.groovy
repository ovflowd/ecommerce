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
 */

class Stock {

    /* Identifiers */
    String id

    /* Attributes */
    String details // Details about this Stock Entry
    Integer amount // Amount added or removed in the stock
    String productId // Product Identifier

    /* Timestamps */
    Date addedAt = new Date() // When it was Added
    Date editedAt = new Date() // Last time Edited

    static constraints = {
        details blank: true, nullable: false
        addedAt blank: true, nullable: true
        editedAt blank: true, nullable: true
        productId blank: false, nullable: false
        amount notEqual: 0
    }

    static mapping = {
        details defaultValue: 'Stock Entry without Description.'
        amount defaultValue: 1
        id generator: 'uuid'
    }
}
