package com.yodgorbek.openmeet.presentation.chat

sealed class ChatIntent {
    data class SendMessage(val message: String) : ChatIntent()
    object LoadMessages : ChatIntent()
}

data class ChatState(
    val isLoading: Boolean = false,
    val messages: List<String> = emptyList(),
    val error: String? = null
)
