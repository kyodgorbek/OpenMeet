package com.yodgorbek.openmeet.presentation.call

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yodgorbek.openmeet.domain.repository.CallRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CallViewModel(
    private val callRepository: CallRepository
) : ViewModel() {

    private val _isCallActive = MutableStateFlow(false)
    val isCallActive: StateFlow<Boolean> = _isCallActive.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            callRepository.getActiveCallId().collect { callId ->
                _isCallActive.value = callId != null
            }
        }
    }

    fun joinCall(callId: String) {
        viewModelScope.launch {
            val result = callRepository.joinCall(callId)
            result.onFailure {
                _error.value = it.message
            }
        }
    }

    fun leaveCall() {
        viewModelScope.launch {
            callRepository.leaveCall()
        }
    }
}
