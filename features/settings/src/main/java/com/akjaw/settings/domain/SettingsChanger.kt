package com.akjaw.settings.domain

import com.akjaw.settings.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SettingsChanger(
    private val inMemorySettingsRepository: Repository, // TODO test for only uses defaults when it doesn't exist
    defaultBooleanSettings: Map<BooleanSettingsOption, Boolean>
) {

    private val mutableSettings = MutableStateFlow(defaultBooleanSettings)
    val booleanSettings: StateFlow<Map<BooleanSettingsOption, Boolean>> = mutableSettings

    fun changeBooleanSetting(setting: BooleanSettingsOption, value: Boolean) {
        val newSettings = mutableSettings.value.toMutableMap()
        newSettings[setting] = value
        mutableSettings.value = newSettings

        inMemorySettingsRepository.setBoolean(option = setting, value = value)
    }
}
