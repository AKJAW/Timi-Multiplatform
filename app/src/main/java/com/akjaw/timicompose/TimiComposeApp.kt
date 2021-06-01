package com.akjaw.timicompose

import android.app.Application
import com.akjaw.core.common.domain.ApplicationInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TimiComposeApp : Application() {

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards ApplicationInitializer>

    override fun onCreate() {
        super.onCreate()

        initializers.forEach { it.initialize() }
    }
}
