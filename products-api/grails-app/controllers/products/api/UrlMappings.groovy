package products.api

class UrlMappings {
    static excludes = []

    static mappings = {
        // List Products / Search Products
        get "/product?"(controller:"products", action:"index")
        // Add a Product
        post "/product"(controller:"products", action:"save")
        // Update a Product
        put "/product/$id"(controller:"products", action:"update")
        // Delete a Product
        delete "/product/$id"(controller:"products", action:"delete")
        // Add Stock Entry
        post "/product/stock"(controller:"stock", action:"save")
        // List Stock
        get "/product/stock?"(controller:"stock", action:"index")
        // Index Page
        get "/"(controller: 'commerce', action:'index')
        // Internal Server Error
        "500"(view: '/error')
        // Page{Method} Not Found
        "404"(view: '/notFound')
    }
}
