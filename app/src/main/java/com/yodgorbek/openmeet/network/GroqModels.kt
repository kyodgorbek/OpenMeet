package com.yodgorbek.openmeet.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroqChatRequest(
    val model: String = "llama3-8b-8192", // Default to an extremely fast model
    val messages: List<GroqMessage>,
    val temperature: Double = 0.7
)

@Serializable
data class GroqMessage(
    val role: String,
    val content: String
)

@Serializable
data class GroqChatResponse(
    val id: String,
    val choices: List<GroqChoice>
)

@Serializable
data class GroqChoice(
    val index: Int,
    val message: GroqMessage,
    @SerialName("finish_reason") val finishReason: String? = null
)
