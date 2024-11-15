package com.even.alpha.poc

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

fun makeJwtVerifier(): JWTVerifier {
    val jwtSecret = "your-256-bit-secret"
    val algorithm = Algorithm.HMAC256(jwtSecret)
    return JWT.require(algorithm)
        .withIssuer("ktor-auth")
        .build()
}

fun generateJwtToken(username: String): String {
    val jwtSecret = "your-256-bit-secret" // Use a strong, secret key in production
    val algorithm = Algorithm.HMAC256(jwtSecret)
    return JWT.create()
        .withIssuer("ktor-auth")
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + 60000)) // Token valid for 1 minute
        .sign(algorithm)
}
