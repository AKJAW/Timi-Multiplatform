package com.akjaw.timicompose

import android.app.Application
import co.touchlab.kampkit.kmmKoinModules
import com.akjaw.core.common.composition.androidCoreModule
import com.akjaw.details.composition.taskDetailsModule
import com.akjaw.settings.composition.settingsModule
import com.akjaw.task.list.composition.taskUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class TimiComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TimiComposeApp)
            modules(allKoinModules)
        }
    }
}

val allKoinModules: List<Module> = listOf(
    *kmmKoinModules.toTypedArray(),
    androidCoreModule,
    taskUiModule,
    taskDetailsModule,
    settingsModule,
)
