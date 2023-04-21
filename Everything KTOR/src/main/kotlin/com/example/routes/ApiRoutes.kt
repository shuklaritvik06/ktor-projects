package com.example.routes

import com.example.database.*
import com.example.dtos.Book
import com.example.dtos.User
import com.example.utils.JwtToken
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val JWTToken = JwtToken(HoconApplicationConfig(ConfigFactory.load()))
fun Routing.apiRoutes(){
    route("/api"){
        post("/insert") {
            val book = call.receive<Book>()
            insertBook(book)
            call.respond(mapOf("message" to "Inserted!"))
        }
        get("/all") {
            getBooks()
            call.respond(arr)
        }
        put("/update/{id}") {
            val id = call.parameters["id"]?.toInt()
            val book = call.receive<Book>()
            if (id != null) {
                updateBook(id,book)
            }
            call.respond("Updated Book!")
        }
        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toInt()
            if (id != null) {
                deleteBook(id)
            }
            call.respond("Deleted book!")
        }
        post("/user/register") {
            val user = call.receive<User>()
            val result: Boolean = registerUser(user)
            if (result){
                call.respond(user)
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Already Exist Username!")
            }
        }
        post("/user/login") {
            val user = call.receive<User>()
            val result: Boolean = loginUser(user)
            if (result){
                call.respond("Logged in!")
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "No user exist with this username Or Wrong Password!")
            }
        }
        post("/user/jwt") {
            val user = call.receive<User>()
            val token = JWTToken.generateToken(user)
            call.respond(mapOf("token" to token))
        }
        post("/user/verify") {
            val token = call.request.headers["Authorization"]?.split(" ")?.get(1)
            val result = token?.let { it1 -> JWTToken.verifyToken(it1) }
            if (!result!!){
                call.respond("Logged in!")
            }else{
                call.respond("Falsy!")
            }
        }
        authenticate ("auth-jwt") {
            get("/dashboard") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
    }
}