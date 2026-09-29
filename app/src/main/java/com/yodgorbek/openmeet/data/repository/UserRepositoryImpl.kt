package com.yodgorbek.openmeet.data.repository

import com.yodgorbek.openmeet.data.datastore.PreferencesManager
import com.yodgorbek.openmeet.data.local.UserDao
import com.yodgorbek.openmeet.data.local.UserEntity
import com.yodgorbek.openmeet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val preferencesManager: PreferencesManager
) : UserRepository {

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    override suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun setDarkTheme(isDark: Boolean) {
        preferencesManager.setDarkTheme(isDark)
    }

    override fun isDarkTheme(): Flow<Boolean> {
        return preferencesManager.isDarkTheme
    }
}
