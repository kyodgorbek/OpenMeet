package com.yodgorbek.openmeet.data.repository

import com.yodgorbek.openmeet.domain.repository.CallRepository
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CallRepositoryImpl(
    private val streamVideo: StreamVideo
) : CallRepository {

    private val _activeCallId = MutableStateFlow<String?>(null)

    override suspend fun joinCall(callId: String): Result<Unit> {
        return try {
            val call = streamVideo.call("default", callId)
            val result = call.join(create = true)
            if (result.isSuccess) {
                call.camera.setEnabled(true)
                call.microphone.setEnabled(true)
                _activeCallId.value = callId
                Result.success(Unit)
            } else {
                Result.failure(Exception(result.errorOrNull()?.message ?: "Failed to join call"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun leaveCall() {
        _activeCallId.value?.let { callId ->
            val call = streamVideo.call("default", callId)
            call.leave()
            _activeCallId.value = null
        }
    }

    override fun getActiveCallId(): Flow<String?> = _activeCallId.asStateFlow()
}
