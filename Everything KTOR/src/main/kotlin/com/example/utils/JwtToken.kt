package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.dtos.User
import io.ktor.server.config.*
import java.util.*

class JwtToken(private val config: HoconApplicationConfig) {
    fun generateToken(user: User): String {
        val audience = config.property("jwt.audience").getString()
        val secret = config.property("jwt.secret").getString()
        val issuer = config.property("jwt.domain").getString()
        val expiry = System.currentTimeMillis() +  60000 * 60
        return JWT.create().withAudience(audience).withExpiresAt(Date(expiry)).withIssuer(issuer).withClaim("username", user.username).sign(
            Algorithm.HMAC256(secret)
        )
    }
    fun verifyToken(token: String): Boolean? {
        return JWT.decode(token).claims["username"]?.isNull
    }
}