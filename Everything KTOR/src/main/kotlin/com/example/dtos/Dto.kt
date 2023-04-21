package com.example.dtos

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class Book(
    val bookName: String,
    val id: Int,
    val author: String,
    val price: Int,
    val isbn: String
)

@Serializable
data class User(
    val username: String,
    val password: String
){
    fun hashPass(): String{
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
    fun checkPass(hashed: String): Boolean{
        return BCrypt.checkpw(password, hashed)
    }
}