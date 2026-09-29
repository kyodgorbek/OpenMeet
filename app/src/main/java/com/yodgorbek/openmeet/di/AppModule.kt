package com.yodgorbek.openmeet.di

import com.yodgorbek.openmeet.BuildConfig
import com.yodgorbek.openmeet.network.GroqApi
import com.yodgorbek.openmeet.network.KtorClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import androidx.room.Room
import com.yodgorbek.openmeet.data.datastore.PreferencesManager
import com.yodgorbek.openmeet.data.local.AppDatabase
import com.yodgorbek.openmeet.presentation.chat.ChatViewModel
import com.yodgorbek.openmeet.domain.repository.ChatRepository
import com.yodgorbek.openmeet.domain.repository.UserRepository
import com.yodgorbek.openmeet.domain.repository.CallRepository
import com.yodgorbek.openmeet.data.repository.ChatRepositoryImpl
import com.yodgorbek.openmeet.data.repository.UserRepositoryImpl
import com.yodgorbek.openmeet.data.repository.CallRepositoryImpl
import com.yodgorbek.openmeet.presentation.call.CallViewModel
import io.getstream.video.android.core.StreamVideoBuilder



val appModule = module {
    // Network
    single { KtorClient.createHttpClient() }
    
    // Groq AI API
    single { GroqApi(httpClient = get(), apiKey = BuildConfig.GROQ_API_KEY) }
    
    // Database
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "openmeet.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().userDao() }

    // DataStore
    single { PreferencesManager(androidContext()) }

    // Stream Chat SDK
    single<io.getstream.chat.android.client.ChatClient> {
        val apiKey = BuildConfig.STREAM_API_KEY
        
        io.getstream.chat.android.client.ChatClient.Builder(apiKey, androidContext())
            .build()
    }

    // Stream Video SDK
    single {
        val apiKey = BuildConfig.STREAM_API_KEY
        val userId = "test-user-1"
        val token = com.yodgorbek.openmeet.network.StreamTokenUtils.generateStreamToken(userId)

        StreamVideoBuilder(
            context = androidContext(),
            apiKey = apiKey,
            user = io.getstream.video.android.model.User(
                id = userId,
                name = "Test User",
                image = "https://bit.ly/2TIt8NR"
            ),
            token = token
        ).build()
    }

    // Repositories
    single<UserRepository> { UserRepositoryImpl(userDao = get(), preferencesManager = get()) }
    single<ChatRepository> { ChatRepositoryImpl(chatClient = get(), groqApi = get()) }
    single<CallRepository> { CallRepositoryImpl(streamVideo = get()) }

    // ViewModels
    viewModel { ChatViewModel(chatRepository = get()) }
    viewModel { CallViewModel(callRepository = get()) }
    viewModel { com.yodgorbek.openmeet.presentation.login.LoginViewModel(userRepository = get()) }
}
