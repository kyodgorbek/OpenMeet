package com.yodgorbek.openmeet.domain.repository

import com.yodgorbek.openmeet.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<UserEntity>>
    suspend fun saveUser(user: UserEntity)
    suspend fun setDarkTheme(isDark: Boolean)
    fun isDarkTheme(): Flow<Boolean>
}
