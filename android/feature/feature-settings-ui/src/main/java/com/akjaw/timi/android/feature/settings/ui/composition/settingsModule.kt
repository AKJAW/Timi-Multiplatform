package com.akjaw.timi.android.feature.settings.ui.composition

import android.content.Context
import android.content.SharedPreferences
import com.akjaw.timi.android.core.data.persistance.SharedPreferencesKeys
import com.akjaw.timi.android.core.domain.ActivityInitializer
import com.akjaw.timi.android.feature.settings.ui.data.InitialSettingsOptionsProvider
import com.akjaw.timi.android.feature.settings.ui.data.ResourcesSystemDarkModeProvider
import com.akjaw.timi.android.feature.settings.ui.data.SettingsRepository
import com.akjaw.timi.android.feature.settings.ui.data.SharedPreferencesSettingsRepository
import com.akjaw.timi.android.feature.settings.ui.data.SystemDarkModeProvider
import com.akjaw.timi.android.feature.settings.ui.domain.DarkThemeInitializer
import com.akjaw.timi.android.feature.settings.ui.domain.SettingsChanger
import com.akjaw.timi.android.feature.settings.ui.presentation.DarkModeThemeStateUpdater
import com.akjaw.timi.android.feature.settings.ui.presentation.SettingsViewModel
import com.akjaw.timi.android.feature.settings.ui.view.SettingsScreenCreator
import com.akjaw.timi.android.feature.settings.ui.view.SettingsScreenCreatorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesKeys.settings,
            Context.MODE_PRIVATE
        )
    }
    factory<SettingsRepository> { SharedPreferencesSettingsRepository(get()) }
    factory { DarkThemeInitializer(get(), get(), get()) } bind ActivityInitializer::class
    factory<SystemDarkModeProvider> { ResourcesSystemDarkModeProvider(androidContext()) }
    factory<SettingsScreenCreator> { SettingsScreenCreatorImpl() }
    factory { InitialSettingsOptionsProvider(get()) }
    factory { SettingsChanger(get(), get()) }
    factory { DarkModeThemeStateUpdater() }
    viewModel {
        SettingsViewModel(get(), get())
    }
}
