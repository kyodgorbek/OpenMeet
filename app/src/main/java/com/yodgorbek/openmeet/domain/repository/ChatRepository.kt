package com.yodgorbek.openmeet.domain.repository

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun fetchAiResponse(prompt: String): Result<String>
    suspend fun connectUser(userId: String, userName: String): Result<Unit>
    fun startListeningToAiChannel(channelId: String, currentUserId: String)
}
