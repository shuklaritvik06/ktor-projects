package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Routing.extras(){
    route("/extras"){
        get("/headers") {
            call.response.headers.append("server","Ktor")
            call.respond("Hello Headers Attached")
        }
        get("/download/attachment") {
            val file = File("files/fileone.png")
            call.response.headers.append(HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName,"download.png").toString()
            )
            call.respondFile(file)
        }
        get("/open/inline") {
            val file = File("files/filetwo.png")
            call.response.headers.append(HttpHeaders.ContentDisposition,
                ContentDisposition.Inline.withParameter(ContentDisposition.Parameters.FileName,"download.png").toString()
            )
            call.respondFile(file)
        }
    }
}