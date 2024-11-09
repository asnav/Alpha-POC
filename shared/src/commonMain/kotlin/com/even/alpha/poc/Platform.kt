package com.even.alpha.poc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform