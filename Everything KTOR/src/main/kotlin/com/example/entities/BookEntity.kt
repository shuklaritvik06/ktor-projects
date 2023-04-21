package com.example.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object BookEntity: Table<Nothing>("book"){
    val id =  int("id").primaryKey()
    val bookName = varchar("bookName")
    val author = varchar("author")
    val price = int("price")
    val isbn = varchar("isbn")
}

