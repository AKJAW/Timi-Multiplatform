package com.akjaw.settings.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.settings.domain.BooleanSettingsOption
import com.akjaw.settings.domain.SettingsChanger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val settingsChanger: SettingsChanger
) : ViewModel() {

    val booleanOptionsFlow: StateFlow<Map<BooleanSettingsOption, Boolean>> =
        settingsChanger.booleanSettings

    fun onSwitchValueChange(setting: BooleanSettingsOption, value: Boolean) {
        settingsChanger.changeBooleanSetting(setting, value)
    }
}
