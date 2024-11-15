package com.even.alpha.poc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AuthScreen() {
    MaterialTheme {
        AuthScreenContent()
    }
}

@Composable
fun AuthScreenContent() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFEEECE4)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            value = username,
            onValueChange = { username = it },
            placeholder = "Username"
        )
        Spacer(modifier = Modifier.height(16.dp))  // Add space between inputs
        AuthField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(24.dp))  // Space between input and button

        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        isLoading = true
                        token = login(username, password)
                        username = ""
                        password = ""
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("Sign In", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))  // Space between button and token text

        if (token.isNotEmpty()) {
            Text(text = "Token: $token", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun AuthField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(56.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        colors = TextFieldDefaults.colors()
    )
}

suspend fun login(username: String, password: String): String {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    try {
        val response: HttpResponse = client.post("http://0.0.0.0:8081/login") {
            contentType(ContentType.Application.Json)
            setBody(UserCredentials(username, password))
        }
        log(response.toString())
            val body: JwtResponse = response.body()
        return body.token
    } catch (e: Exception) {
        log("start request")
        e.printStackTrace()
        return "Login failed"
    }
}