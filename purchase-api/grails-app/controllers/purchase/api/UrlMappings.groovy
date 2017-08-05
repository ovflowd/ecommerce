package purchase.api

class UrlMappings {
    static mappings = {
        // List Products / Search Products
        get "/cart/?"(controller:"cart", action:"index")
        // Add a Cart
        post "/cart"(controller:"cart", action:"create")
        // Remove a Cart
        delete "/cart/$id"(controller:"cart", action:"delete")
        // Add a Cart Item
        post "/cart/items/"(controller:"cart", action:"include")
        // Remove a Cart Item
        delete "/cart/items/?"(controller:"cart", action:"remove")
        // Remove a Cart Item
        delete "/cart/items/$id"(controller:"cart", action:"removeById")
        // Checkout Cart
        post "/cart/checkout"(controller:"cart", action:"checkout")
        // Index Page
        get "/"(controller: 'application', action:'index')
        // Internal Server Error
        "500"(view: '/error')
        // Page{Method} Not Found
        "404"(view: '/notFound')
    }
}
