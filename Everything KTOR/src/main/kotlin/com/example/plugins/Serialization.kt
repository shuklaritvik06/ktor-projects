package com.example.plugins

import com.example.routes.apiRoutes
import com.example.routes.userRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        userRoutes()
        apiRoutes()
    }
}
