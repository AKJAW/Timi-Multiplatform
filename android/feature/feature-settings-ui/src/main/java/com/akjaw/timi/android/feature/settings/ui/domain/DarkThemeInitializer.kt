package com.akjaw.timi.android.feature.settings.ui.domain

import com.akjaw.timi.android.core.domain.ActivityInitializer
import com.akjaw.timi.android.feature.settings.ui.data.SettingsRepository
import com.akjaw.timi.android.feature.settings.ui.data.SystemDarkModeProvider
import com.akjaw.timi.android.feature.settings.ui.presentation.DarkModeThemeStateUpdater

class DarkThemeInitializer(
    private val settingsRepository: SettingsRepository,
    private val systemDarkModeProvider: SystemDarkModeProvider,
    private val darkModeThemeStateUpdater: DarkModeThemeStateUpdater
) : ActivityInitializer {

    override fun initialize() {
        val persistedValue = if (isDarkModeAlreadyPersisted().not()) {
            val newValue = systemDarkModeProvider.isDarkModeEnabled()
            settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, newValue)
            newValue
        } else {
            settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
        }
        darkModeThemeStateUpdater.changeDarkModeValue(persistedValue)
    }

    private fun isDarkModeAlreadyPersisted() =
        settingsRepository.containsOption(BooleanSettingsOption.DARK_MODE)
}
