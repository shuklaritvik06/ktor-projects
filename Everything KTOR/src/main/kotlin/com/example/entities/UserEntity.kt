package com.example.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity: Table<Nothing>("user"){
    val userName = varchar("username")
    val password = varchar("password")
    val id = int("id").primaryKey()
}
