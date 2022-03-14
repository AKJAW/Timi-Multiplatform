package com.akjaw.timicompose

import android.app.Application
import co.touchlab.kampkit.initKoin
import dagger.hilt.android.HiltAndroidApp
import org.koin.dsl.module

@HiltAndroidApp
class TimiComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            module {

            }
        )
    }
}
