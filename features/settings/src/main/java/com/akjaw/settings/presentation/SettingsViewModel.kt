package com.akjaw.settings.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.settings.domain.SettingsOption

internal class SettingsViewModel(defaultSettings: Boolean) : ViewModel() {

    var settings = defaultSettings

    fun changeSwitchSetting(setting: SettingsOption, value: Boolean) {
        settings = value
    }
}
