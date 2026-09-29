package com.yodgorbek.openmeet.presentation.chat

import androidx.compose.runtime.Composable
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(onBackPressed: () -> Unit) {
    // We will assume a default channel ID for the AI chat.
    val channelId = "messaging:ai-assistant"
    
    // Instantiate our custom ViewModel so it starts listening to messages
    val chatViewModel: ChatViewModel = koinViewModel()
    
    MessagesScreen(
        viewModelFactory = MessagesViewModelFactory(
            context = androidx.compose.ui.platform.LocalContext.current,
            channelId = channelId
        ),
        onBackPressed = onBackPressed
    )
}
