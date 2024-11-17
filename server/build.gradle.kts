plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "com.even.alpha.poc"
version = "1.0.0"
application {
    mainClass.set("com.even.alpha.poc.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    // Core Ktor dependencies
    implementation(libs.ktor.server.core) // Core server functionality
    implementation(libs.ktor.server.netty) // Netty engine for embedded server
    implementation(libs.ktor.serialization.kotlinx.json) // JSON serialization
    implementation(libs.ktor.server.auth) // Authentication feature
    implementation(libs.ktor.server.auth.jwt) // JWT authentication
    implementation(libs.ktor.server.content.negotiation) // Content negotiation
    implementation(libs.ktor.server.cio.jvm) // CIO engine, optional if using CIO instead of Netty

    // For handling key store operations in SSL configuration
    implementation(libs.ktor.network) // Provides key store and sslConnector functionality
    implementation(libs.ktor.network.tls.certificates)

    // For JSON serialization (Kotlinx serialization)
    implementation(libs.kotlinx.serialization.json)

    // For key store handling
    implementation(libs.kotlin.stdlib.jdk8) // Basic Kotlin stdlib

    implementation(project(":shared"))
}