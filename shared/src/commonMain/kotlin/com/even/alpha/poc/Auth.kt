package com.even.alpha.poc

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(val username: String, val password: String)

@Serializable
data class JwtResponse(val token: String)