package com.akjaw.timicompose

import android.app.Application
import android.content.Context
import co.touchlab.kampkit.initKoin
import com.akjaw.details.composition.taskDetailsModule
import com.akjaw.settings.composition.settingsModule
import com.akjaw.task.list.composition.taskListModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.dsl.module

@HiltAndroidApp
class TimiComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            listOf(
                taskListModule,
                taskDetailsModule,
                settingsModule,
                module {
                    single<Context> { this@TimiComposeApp.applicationContext }
                }
            )
        )
    }
}
