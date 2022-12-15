package com.akjaw.timi.android.feature.settings.ui.domain

import com.akjaw.timi.android.feature.settings.ui.data.InitialSettingsOptionsProvider
import com.akjaw.timi.android.feature.settings.ui.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SettingsChanger(
    private val settingsRepository: SettingsRepository,
    initialSettingsOptionsProvider: InitialSettingsOptionsProvider,
) {

    private val mutableSettings = MutableStateFlow(initialSettingsOptionsProvider.get())
    val booleanSettings: StateFlow<Map<BooleanSettingsOption, Boolean>> = mutableSettings

    fun changeBooleanSetting(setting: BooleanSettingsOption, value: Boolean) {
        val newSettings = mutableSettings.value.toMutableMap()
        newSettings[setting] = value
        mutableSettings.value = newSettings

        settingsRepository.setBoolean(option = setting, value = value)
    }
}
