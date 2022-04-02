package com.akjaw.timicompose

import android.app.Application
import android.content.Context
import co.touchlab.kampkit.initKoin
import com.akjaw.core.common.composition.androidCoreModule
import com.akjaw.details.composition.taskDetailsModule
import com.akjaw.settings.composition.settingsModule
import com.akjaw.task.list.composition.taskListModule
import org.koin.dsl.module

class TimiComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            listOf(
                androidCoreModule,
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
