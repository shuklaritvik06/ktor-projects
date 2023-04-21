package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

val users = arrayListOf<Any>()

@Serializable
data class User(val email: String, val password: String)
fun Routing.userRoutes(){
    route("/user"){
        post("/login") {
            val user = call.receive<User>()
            user?.let { it->users.add(it) }
            call.respond(user)
        }
        get("/all") {
            call.respond(mapOf("users" to users.toString()))
        }
    }
}