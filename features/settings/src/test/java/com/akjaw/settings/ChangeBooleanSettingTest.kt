package com.akjaw.settings

import com.akjaw.settings.data.InMemorySettingsRepository
import com.akjaw.settings.data.Repository
import com.akjaw.settings.domain.BooleanSettingsOption
import com.akjaw.settings.domain.SettingsChanger
import com.akjaw.settings.presentation.SettingsViewModel
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

// the setting is persisted - Here
// on start the setting is taken from the settings - RetrievingSettingsTest
// Shows the correct list of settings - RetrievingSettingsTest
internal class ChangeBooleanSettingTest {

    private lateinit var systemUnderTest: SettingsViewModel

    @Test
    fun `Changing to true is reflected in the state`() {
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DarkMode to true))

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DarkMode, true)

        val result = systemUnderTest.booleanSettingsFlow.value
        expectThat(result[BooleanSettingsOption.DarkMode]).isTrue()
    }

    @Test
    fun `Changing to false is reflected in the state`() {
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DarkMode to true))

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DarkMode, false)

        val result = systemUnderTest.booleanSettingsFlow.value
        expectThat(result[BooleanSettingsOption.DarkMode]).isFalse()
    }

    @Test
    fun `The changed value is persisted`() {
        val repository = InMemorySettingsRepository()
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DarkMode to true), repository)

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DarkMode, true)

        val result = repository.getBoolean(BooleanSettingsOption.DarkMode)
        expectThat(result).isTrue()
    }
}

internal fun prepareViewModel(
    defaultBooleanSettings: Map<BooleanSettingsOption, Boolean> = mapOf(),
    repository: Repository = InMemorySettingsRepository(),
): SettingsViewModel {
    val settingsChanger = SettingsChanger(repository, defaultBooleanSettings)
    return SettingsViewModel(settingsChanger)
}
