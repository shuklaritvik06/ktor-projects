package com.example.plugins

import com.example.routes.bookRoutes
import com.example.routes.extras
import com.example.routes.userRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        static("/static") {
            resources("static")
        }
        bookRoutes()
        extras()
    }
}
