package com.yodgorbek.openmeet.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class GroqApi(
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    companion object {
        private const val GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions"
    }

    suspend fun getChatCompletion(prompt: String): Result<String> {
        return try {
            val requestBody = GroqChatRequest(
                messages = listOf(
                    GroqMessage(role = "system", content = "You are a helpful AI assistant inside the OpenMeet app."),
                    GroqMessage(role = "user", content = prompt)
                )
            )

            val response: GroqChatResponse = httpClient.post(GROQ_API_URL) {
                header(HttpHeaders.Authorization, "Bearer $apiKey")
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }.body()

            val reply = response.choices.firstOrNull()?.message?.content ?: "No response from AI."
            Result.success(reply)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
