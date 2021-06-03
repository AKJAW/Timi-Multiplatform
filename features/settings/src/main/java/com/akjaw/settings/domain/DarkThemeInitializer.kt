package com.akjaw.settings.domain

import com.akjaw.core.common.domain.ActivityInitializer
import com.akjaw.core.common.view.theme.ThemeState
import com.akjaw.settings.data.SettingsRepository
import com.akjaw.settings.data.SystemDarkModeProvider
import javax.inject.Inject

class DarkThemeInitializer @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val systemDarkModeProvider: SystemDarkModeProvider
) : ActivityInitializer {

    override fun initialize() {
        val persistedValue = if (isDarkModeAlreadyPersisted().not()) {
            val newValue = systemDarkModeProvider.isDarkModeEnabled()
            settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, newValue)
            newValue
        } else {
            settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
        }
        ThemeState.isDarkTheme.value = persistedValue
    }

    private fun isDarkModeAlreadyPersisted() =
        settingsRepository.containsOption(BooleanSettingsOption.DARK_MODE)
}
