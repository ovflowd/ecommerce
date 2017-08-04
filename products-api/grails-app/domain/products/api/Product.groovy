package products.api

/**
 * Product Class
 *
 * A Product it's an item that can be stored and sell.
 * Products have different attributes.
 *
 * @observation the amount in stock it's picked from the Stock history,
 *  stock amount can't be negative.
 */

class Product {

    /* Identifiers */
    String id

    /* Attributes */
    String name // The name of the Product
    String description // The description of the Product
    Float price // Price of the Product
    Integer stock = 0 // Amount in Stock

    /* Timestamps */
    Date addedAt = new Date() // When it was Added
    Date editedAt = new Date() // Last time Edited

    static constraints = {
        name blank:false, nullable: false
        description blank:false, nullable: false
        price blank:false, nullable: false, notEqual: 0F
        stock blank: true, nullable: true
        addedAt blank:false, nullable: false
        editedAt blank:false, nullable: false
    }

    static mapping = {
        description defaultValue: 'Product without Description'
        price defultValue: 1.0
        id generator: 'uuid'
        stock insertable: false, updateable: true, defaultValue: 0
        table: 'product'
    }
}
