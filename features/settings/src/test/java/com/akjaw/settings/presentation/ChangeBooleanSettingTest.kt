package com.akjaw.settings.presentation

import com.akjaw.settings.domain.SettingsChanger
import com.akjaw.settings.domain.SettingsOption
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

// changing a setting is reflected in the view model state
// the setting is persisted - Here
// on start the setting is taken from the settings - RetrievingSettingsTest
// Shows the correct list of settings - RetrievingSettingsTest
internal class ChangeBooleanSettingTest {

    private lateinit var systemUnderTest: SettingsViewModel

    @Test
    fun `Changing to true is reflected in the state`() {
        val settingsChanger =
            SettingsChanger(mapOf(SettingsOption.DARK_MODE to false))
        systemUnderTest = SettingsViewModel(settingsChanger)

        systemUnderTest.onSwitchValueChange(SettingsOption.DARK_MODE, true)

        val result = systemUnderTest.settingsFlow.value
        expectThat(result[SettingsOption.DARK_MODE]).isTrue()
    }

    @Test
    fun `Changing to false is reflected in the state`() {
        val settingsChanger =
            SettingsChanger(mapOf(SettingsOption.DARK_MODE to true))
        systemUnderTest = SettingsViewModel(settingsChanger)

        systemUnderTest.onSwitchValueChange(SettingsOption.DARK_MODE, false)

        val result = systemUnderTest.settingsFlow.value
        expectThat(result[SettingsOption.DARK_MODE]).isFalse()
    }
}
