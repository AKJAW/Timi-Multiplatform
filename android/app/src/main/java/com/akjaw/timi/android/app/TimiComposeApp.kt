package com.akjaw.timi.android.app

import android.app.Application
import com.akjaw.timi.android.core.composition.androidCoreModule
import com.akjaw.timi.android.feature.settings.ui.composition.settingsModule
import com.akjaw.timi.kmp.feature.task.dependency.detail.composition.taskDetailsModule
import com.akjaw.timi.kmp.shared.kmmKoinModules
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
    taskDetailsModule,
    settingsModule,
)
