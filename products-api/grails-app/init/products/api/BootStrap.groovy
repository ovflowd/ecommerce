package products.api

import grails.util.Environment
import groovy.sql.Sql

/**
 * Class Bootstrap
 *
 * This class handles with business logic that happens on the startup of Grails
 */
class BootStrap {
    def init = { servletContext ->
        // If we are on the Development Environment, let's created the tables and add some mock data.
        if (Environment.current == Environment.DEVELOPMENT) {
            // Create Tables
            Sql.newInstance().execute(new File('src/main/sql/products-api.sql').text)
            // We add some Products
            def javaProduct = new Product(name: 'Java for Dummies (For Dummies (Computers))', description: '6th ed. Edition - by Barry Burd PH.D.', price: 36.34).save()
            def linkedinProduct = new Product(name: 'Linkedin Premium Subscription', description: 'A voucher for a single month Linkedin Career Premium Subscription', price: 15.6).save()
            new Product(name: 'Beginning Groovy and Grails: From Novice to Professional', description: '1st Edition - by Christopher M. Judd (Author), Joseph Faisal Nusairat', price: 38.21).save()

            // We add single Stock entries for those Products
            new Stock(details: 'Lojas Americanas added Stock for Java For Dummies Book', amount: 20, productId: javaProduct.id).save()
            new Stock(details: 'Linkedin has now 20000 more slots for Linkedin Premium Vouchers. Get yours now.', amount: 20000, productId: linkedinProduct.id).save()

            // Since we're not in a Controller we need manually set the amount of stock
            javaProduct.properties.stock = 20
            linkedinProduct.properties.stock = 20000
        }
        // If we are on the Development Environment, let's create the tables
        if (Environment.current == Environment.TEST) {
            // Create Tables
            Sql.newInstance().execute(new File('src/main/sql/products-api.sql').text)
        }
    }

    def destroy = {}
}
