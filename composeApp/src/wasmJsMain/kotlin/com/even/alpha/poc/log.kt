package com.even.alpha.poc

actual fun log(message: String, tag: String) {
    console.log("[$tag]: $message") // Logs to the browser console
}