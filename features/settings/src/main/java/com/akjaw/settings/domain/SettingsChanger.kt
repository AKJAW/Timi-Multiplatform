package com.akjaw.settings.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsChanger(defaultSettings: Map<SettingsOption, Boolean>) {

    private val mutableSettings = MutableStateFlow(defaultSettings)
    val settings: StateFlow<Map<SettingsOption, Boolean>> = mutableSettings

    fun changeBooleanSetting(setting: SettingsOption, value: Boolean) {
        val newSettings = mutableSettings.value.toMutableMap()
        newSettings[setting] = value
        mutableSettings.value = newSettings
    }
}
