package purchase.api

/**
 * Log Class
 *
 * A Log it's a history entry about operations on the system
 */
class Log {

    /* Identifiers */
    Long id // Log unique identifier

    /* Attributes */
    String relatedTable // Related Domain for this Log Entry
    String relatedElement // Related Domain Identifier for this Log Entry
    String details // Details about the Log entry

    /* Timestamps */
    Date addedAt = new Date() // When it was Added

    static constraints = {
        relatedTable blank: false, nullable: false
        relatedElement blank: false, nullable: false
        details blank: false, nullable: false
        addedAt blank: true, nullable: true
    }
}
