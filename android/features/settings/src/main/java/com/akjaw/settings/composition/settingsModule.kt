package com.akjaw.settings.composition

import android.content.Context
import android.content.SharedPreferences
import com.akjaw.core.common.data.persistance.SharedPreferencesKeys
import com.akjaw.core.common.domain.ActivityInitializer
import com.akjaw.settings.data.InitialSettingsOptionsProvider
import com.akjaw.settings.data.ResourcesSystemDarkModeProvider
import com.akjaw.settings.data.SettingsRepository
import com.akjaw.settings.data.SharedPreferencesSettingsRepository
import com.akjaw.settings.data.SystemDarkModeProvider
import com.akjaw.settings.domain.DarkThemeInitializer
import com.akjaw.settings.domain.SettingsChanger
import com.akjaw.settings.presentation.DarkModeThemeStateUpdater
import com.akjaw.settings.presentation.SettingsViewModel
import com.akjaw.settings.view.SettingsScreenCreator
import com.akjaw.settings.view.SettingsScreenCreatorImpl
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
