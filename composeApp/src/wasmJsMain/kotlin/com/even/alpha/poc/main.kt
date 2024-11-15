package com.even.alpha.poc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color(119,119,114)),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier
                .width(300.dp)
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp))
            ){
                AuthScreen()
            }
        }
    }
}