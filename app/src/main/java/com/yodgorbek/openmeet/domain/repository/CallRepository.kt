package com.yodgorbek.openmeet.domain.repository

import kotlinx.coroutines.flow.Flow

interface CallRepository {
    suspend fun joinCall(callId: String): Result<Unit>
    suspend fun leaveCall()
    fun getActiveCallId(): Flow<String?>
}
