package com.akjaw.settings.domain

import com.akjaw.settings.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SettingsChanger(
    private val inMemorySettingsRepository: Repository, // TODO test for only uses defaults when it doesn't exist
    defaultSettings: Map<SettingsOption, Boolean>
) {

    private val mutableSettings = MutableStateFlow(defaultSettings)
    val settings: StateFlow<Map<SettingsOption, Boolean>> = mutableSettings

    fun changeBooleanSetting(setting: SettingsOption, value: Boolean) {
        val newSettings = mutableSettings.value.toMutableMap()
        newSettings[setting] = value
        mutableSettings.value = newSettings

        inMemorySettingsRepository.setBoolean(key = setting, value = value)
    }
}
