package com.akjaw.settings.presentation

import com.akjaw.settings.domain.SettingsOption
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

// changing a setting is reflected in the view model state
// the setting is persisted
// on start the setting is taken from the settings
// Shows the correct list of settings
internal class ChangeBooleanSettingTest {

    private lateinit var systemUnderTest: SettingsViewModel

    @BeforeEach
    fun setUp() {
        systemUnderTest = SettingsViewModel(true)
    }

    @Test
    fun `Changing to true is reflected in the state`() {
        systemUnderTest.changeSwitchSetting(SettingsOption.DARK_MODE, true)

        expectThat(systemUnderTest.settings).isTrue()
    }

    @Test
    fun `Changing to false is reflected in the state`() {
        systemUnderTest = SettingsViewModel(true)

        systemUnderTest.changeSwitchSetting(SettingsOption.DARK_MODE, false)

        expectThat(systemUnderTest.settings).isFalse()
    }
}
