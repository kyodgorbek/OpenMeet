package com.yodgorbek.openmeet.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yodgorbek.openmeet.data.local.UserEntity
import com.yodgorbek.openmeet.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun login(username: String) {
        if (username.isNotBlank()) {
            viewModelScope.launch {
                userRepository.saveUser(
                    UserEntity(
                        id = username.lowercase().replace(" ", "_"),
                        name = username,
                        avatarUrl = "https://bit.ly/2TIt8NR"
                    )
                )
                _isLoggedIn.value = true
            }
        }
    }
}
