package com.akjaw.settings.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.settings.domain.SettingsChanger
import com.akjaw.settings.domain.SettingsOption
import kotlinx.coroutines.flow.StateFlow

internal class SettingsViewModel(private val settingsChanger: SettingsChanger) : ViewModel() {

    val settingsFlow: StateFlow<Map<SettingsOption, Boolean>> = settingsChanger.settings

    fun onSwitchValueChange(setting: SettingsOption, value: Boolean) {
        settingsChanger.changeBooleanSetting(setting, value)
    }
}
