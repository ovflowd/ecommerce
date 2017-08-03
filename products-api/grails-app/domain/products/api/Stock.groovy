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
    Long id

    /* Attributes */
    Long productId // The Related Product Id
    String details // Details about this Stock Entry
    Integer amount // Amount added or removed in the stock

    /* Timestamps */
    Date addedAt // When it was Added
    Date editedAt // Last time Edited

    static constraints = {
        productId blank: false, nullable: false
        details blank: true, nullable: false
        addedAt blank: true, nullable: true
        editedAt blank: true, nullable: true
    }

    static mapping = {
        addedAt defaultValue: new Date().format('yyyyMMdd')
        editedAt defaultValue: new Date().format('yyyyMMdd')
        details defaultValue: "No Details were added."
        amount defaultValue: 0
    }
}
