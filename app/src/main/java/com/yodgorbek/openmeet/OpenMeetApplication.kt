package com.yodgorbek.openmeet

import android.app.Application
import com.yodgorbek.openmeet.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OpenMeetApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 1. Initialize Koin (Dependency Injection)
        startKoin {
            androidLogger()
            androidContext(this@OpenMeetApplication)
            modules(appModule)
        }

    }
}
