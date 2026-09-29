package com.yodgorbek.openmeet.presentation.chat

import androidx.lifecycle.ViewModel
import com.yodgorbek.openmeet.domain.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()
    
    private val channelId = "messaging:ai-assistant"
    private val currentUserId = "test-user-1"

    init {
        // Start listening to the AI channel directly via the repository
        chatRepository.startListeningToAiChannel(channelId, currentUserId)
    }

    fun processIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.LoadMessages -> loadMessages()
            is ChatIntent.SendMessage -> sendMessage(intent.message)
        }
    }

    private fun loadMessages() {
        _state.value = _state.value.copy(isLoading = true)
        _state.value = _state.value.copy(isLoading = false, messages = listOf("Welcome to OpenMeet AI!"))
    }

    private fun sendMessage(msg: String) {
        val current = _state.value.messages.toMutableList()
        current.add(msg)
        _state.value = _state.value.copy(messages = current)
    }
}
