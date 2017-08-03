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
    Long id

    /* Attributes */
    String name // The name of the Product
    String description // The description of the Product
    Integer stock = Stock.findAllWhere(productId: id).amount.sum() as Integer // Quantity on Stock
    Float price // Price of the Product

    /* Timestamps */
    Date addedAt // When it was Added
    Date editedAt // Last time Edited

    static constraints = {
        name blank:false, nullable: false
        description blank:false, nullable: false
        stock blank:false, nullable: false
        price blank:false, nullable: false
        addedAt blank:true, nullable: true
        editedAt blank:true, nullable: true
    }

    static mapping = {
        addedAt defaultValue: new Date().format( 'yyyyMMdd' )
        editedAt defaultValue: new Date().format( 'yyyyMMdd' )
        description defaultValue: "No Description were added."
        stock defaultValue: 0
        price defultValue: 0.0
    }
}
