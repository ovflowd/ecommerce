package products.api

class UrlMappings {
    static excludes = []

    static mappings = {
        // List Products / Search Products
        get "/product?"(controller:"products", action:"index")
        // Add a Product
        post "/product"(controller:"products", action:"save")
        // Update a Product
        put "/product"(controller:"products", action:"update")
        // Update Stock
        put "/product"(controller:"stock", action:"update")
        // List Stock
        get "/product?"(controller:"stock", action:"index")
        // Index Page
        get "/"(controller: 'application', action:'index')
        // Internal Server Error
        "500"(view: '/error')
        // Page{Method} Not Found
        "404"(view: '/notFound')
    }
}
