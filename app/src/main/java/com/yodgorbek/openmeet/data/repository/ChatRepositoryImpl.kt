package com.yodgorbek.openmeet.data.repository

import com.yodgorbek.openmeet.domain.repository.ChatRepository
import com.yodgorbek.openmeet.network.GroqApi
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatRepositoryImpl(
    private val chatClient: ChatClient,
    private val groqApi: GroqApi
) : ChatRepository {

    override suspend fun fetchAiResponse(prompt: String): Result<String> {
        return groqApi.getChatCompletion(prompt)
    }

    override suspend fun connectUser(userId: String, userName: String): Result<Unit> = suspendCoroutine { continuation ->
        val user = User(
            id = userId,
            name = userName,
            image = "https://bit.ly/2TIt8NR"
        )
        val token = com.yodgorbek.openmeet.network.StreamTokenUtils.generateStreamToken(userId)
        chatClient.connectUser(user = user, token = token).enqueue { result ->
            if (result.isSuccess) {
                continuation.resume(Result.success(Unit))
            } else {
                val errorMessage = result.errorOrNull()?.message ?: "Unknown connection error"
                continuation.resume(Result.failure(Exception(errorMessage)))
            }
        }
    }

    override fun startListeningToAiChannel(channelId: String, currentUserId: String) {
        chatClient.subscribe { event ->
            if (event is NewMessageEvent && event.cid == channelId) {
                val message = event.message
                if (message.user.id == currentUserId) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = fetchAiResponse(message.text)
                        response.onSuccess { aiResponse ->
                            val aiMessage = Message(
                                text = aiResponse,
                                cid = channelId
                            )
                            chatClient.channel(channelId).sendMessage(aiMessage).enqueue()
                        }
                    }
                }
            }
        }
    }
}
