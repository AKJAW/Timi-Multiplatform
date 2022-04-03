package com.akjaw.settings.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.settings.domain.BooleanSettingsOption
import com.akjaw.settings.domain.SettingsChanger
import kotlinx.coroutines.flow.StateFlow


internal class SettingsViewModel (
    private val settingsChanger: SettingsChanger,
    private val darkModeThemeStateUpdater: DarkModeThemeStateUpdater
) : ViewModel() {

    val booleanOptionsFlow: StateFlow<Map<BooleanSettingsOption, Boolean>> =
        settingsChanger.booleanSettings

    fun onSwitchValueChange(setting: BooleanSettingsOption, value: Boolean) {
        settingsChanger.changeBooleanSetting(setting, value)
        if (setting == BooleanSettingsOption.DARK_MODE) {
            darkModeThemeStateUpdater.changeDarkModeValue(value)
        }
    }
}