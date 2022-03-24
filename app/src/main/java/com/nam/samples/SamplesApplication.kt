package com.nam.samples

import android.app.Application
import com.nam.samples.google.drive.di.googleDriveModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class SamplesApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SamplesApplication)
            modules(
                googleDriveModule
            )
        }
    }
}