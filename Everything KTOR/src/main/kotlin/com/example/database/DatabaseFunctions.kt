package com.example.database

import com.example.dtos.Book
import com.example.dtos.User
import com.example.entities.BookEntity
import com.example.entities.UserEntity
import org.ktorm.database.Database
import org.ktorm.dsl.*

lateinit var database: Database
lateinit var arr: ArrayList<Book>

fun connect(){
    database = Database.connect(url = "jdbc:mysql://localhost:3306/api", driver = "com.mysql.cj.jdbc.Driver", user = "root", password = "ritvik")
}

fun insertBook(data: Book){
    database.insert(BookEntity){
        set(it.id, data.id)
        set(it.bookName, data.bookName)
        set(it.author,data.author)
        set(it.price,data.price)
        set(it.isbn, data.isbn)
    }
}

fun getBooks(){
    arr = arrayListOf<Book>()
    val books = database.from(BookEntity).select()
    for (book in books){
        val name = book[BookEntity.bookName]
        val author = book[BookEntity.author]
        val price = book[BookEntity.price]
        val isbn = book[BookEntity.isbn]
        val id = book[BookEntity.id]
        lateinit var newBook: Book
        if (name != null && isbn != null && author != null && price != null) {
             newBook = id?.let { Book(name, it,author, price, isbn) }!!
        }
        arr.add(newBook)
    }
}

fun updateBook(id: Int, data: Book){
    database.update(BookEntity){
        set(it.bookName, data.bookName)
        set(it.author,data.author)
        set(it.price,data.price)
        set(it.isbn, data.isbn)
        where { it.id.eq(id) }
    }
}

fun deleteBook(id:Int){
    database.delete(BookEntity){
        it.id.eq(id)
    }
}

fun registerUser(data: User): Boolean{
    val userFound = database.from(UserEntity).select().where {
        UserEntity.userName.eq(data.username)
    }.map {
        it[UserEntity.userName]
    }.firstOrNull()
    if (userFound!=null){
        return false
    }
    database.insert(UserEntity){
        set(it.id, (Math.random()*100).toInt())
        set(it.userName, data.username)
        set(it.password,data.hashPass())
    }
    return true
}

fun loginUser(user: User): Boolean {
    val passHash = database.from(UserEntity).select().where {
        UserEntity.userName.eq(user.username)
    }.map {
        it[UserEntity.password]
    }.firstOrNull()
    if (passHash!=null){
        return user.checkPass(passHash)
    }
    return false
}