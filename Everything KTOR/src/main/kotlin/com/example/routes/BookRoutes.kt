package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val books = arrayListOf<String>()

fun Routing.bookRoutes(){
    route("/book"){
        get("/{id}") {
            val id = call.parameters["id"]?.toInt()
            id?.let { it1 -> books[it1-1] }?.let { it2 -> call.respond(it2) }
        }
        get("/all") {
            call.respond(books)
        }
        post("/add") {
            val params = call.request.queryParameters
            params["book"]?.let { it1 -> books.add(it1) }
        }
        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toInt()
            if (id != null) {
                books.removeAt(id-1)
                call.respond("Book Deleted!")
            }else{
                call.respond("Book not found!")
            }
        }
        put("/update/{id}") {
            val id = call.parameters["id"]?.toInt()
            val params = call.request.queryParameters
            if (id!=null){
                books[id-1] = params["book"].toString()
                call.respond("Book Updated!")
            }
        }
    }
}
