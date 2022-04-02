package com.akjaw.settings.domain

import com.akjaw.core.common.domain.ActivityInitializer
import com.akjaw.settings.data.SettingsRepository
import com.akjaw.settings.data.SystemDarkModeProvider
import com.akjaw.settings.presentation.DarkModeThemeStateUpdater

class DarkThemeInitializer(
    private val settingsRepository: SettingsRepository,
    private val systemDarkModeProvider: SystemDarkModeProvider,
    private val darkModeThemeStateUpdater: DarkModeThemeStateUpdater,
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
