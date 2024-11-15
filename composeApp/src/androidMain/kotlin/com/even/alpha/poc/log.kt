package com.even.alpha.poc

import android.util.Log

actual fun log(message: String, tag: String) {
    Log.d(tag, message) // Logs to Logcat
}