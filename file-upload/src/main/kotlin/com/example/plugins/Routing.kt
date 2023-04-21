package com.example.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun PartData.FileItem.save(path: String): String {
    val fileBytes = streamProvider().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val fileName = UUID.randomUUID().toString() + "." + fileExtension
    val folder = File(path)
    folder.mkdir()
    File("$path/$fileName").writeBytes(fileBytes)
    return fileName
}

fun Application.configureRouting() {
    routing {
        post("/file") {
           val multipart = call.receiveMultipart()
            var fileName: String? = null
            try{
                multipart.forEachPart { partData ->
                    when(partData){
                        is PartData.FileItem ->{
                            fileName = partData.save("src/main/resources/uploads")
                        }
                        else -> {}
                    }
                }
                val imageUrl = "/uploads/$fileName"
                call.respond(HttpStatusCode.OK,imageUrl)
            } catch (ex: Exception) {
                File("${"./uploads"}/$fileName").delete()
                call.respond(HttpStatusCode.InternalServerError,"Error")
            }
        }
        staticResources("/uploads","uploads"){
            this.enableAutoHeadResponse()
        }
    }
}
