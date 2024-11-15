package com.even.alpha.poc

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.routing.post
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import java.io.File
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.*
import java.io.*
import java.security.KeyStore

fun main() {
    embeddedServer(Netty, port = SERVER_PORT_HTTPS, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(Authentication) {
        jwt {
            verifier(makeJwtVerifier())
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") JWTPrincipal(credential.payload) else null
            }
        }
    }
    routing {
        post("/login") {
            val credentials = call.receive<UserCredentials>()
            // Here, no verification is done. The token is generated unconditionally.
            val token = generateJwtToken(credentials.username)
            call.respond(mapOf("token" to token))
        }
    }
}

private fun ApplicationEngine.Configuration.envConfig() {

    val keyStoreFile = File("build/keystore.jks")
    val keyStore = buildKeyStore {
        certificate("sampleAlias") {
            password = "foobar"
            domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
        }
    }
    keyStore.saveToFile(keyStoreFile, "123456")

    connector {
        port = 8080
    }
    sslConnector(
        keyStore = keyStore,
        keyAlias = "sampleAlias",
        keyStorePassword = { "123456".toCharArray() },
        privateKeyPassword = { "foobar".toCharArray() }) {
        port = 8443
        keyStorePath = keyStoreFile
    }
}

