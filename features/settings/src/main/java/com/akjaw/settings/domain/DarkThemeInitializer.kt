package com.akjaw.settings.domain

import com.akjaw.core.common.view.theme.ThemeState
import com.akjaw.settings.data.SettingsRepository

class DarkThemeInitializer(
    private val settingsRepository: SettingsRepository,
    private val systemDarkModeProvider: SystemDarkModeProvider
) {

    fun initialize() {
        val persistedValue = if (isDarkModeAlreadyPersisted().not()) {
            val newValue = systemDarkModeProvider.get()
            settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, newValue)
            newValue
        } else {
            settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
        }
        ThemeState.isDarkTheme.value = persistedValue
    }

    private fun isDarkModeAlreadyPersisted() =
        settingsRepository.containsOption(BooleanSettingsOption.DARK_MODE)

    interface SystemDarkModeProvider {

        fun get(): Boolean
    }
}
