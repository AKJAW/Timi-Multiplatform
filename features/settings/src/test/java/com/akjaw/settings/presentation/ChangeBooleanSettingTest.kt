package com.akjaw.settings.presentation

import com.akjaw.settings.data.InMemorySettingsRepository
import com.akjaw.settings.data.Repository
import com.akjaw.settings.domain.SettingsChanger
import com.akjaw.settings.domain.SettingsOption
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
        systemUnderTest = prepareViewModel(mapOf(SettingsOption.DARK_MODE to true))

        systemUnderTest.onSwitchValueChange(SettingsOption.DARK_MODE, true)

        val result = systemUnderTest.settingsFlow.value
        expectThat(result[SettingsOption.DARK_MODE]).isTrue()
    }

    @Test
    fun `Changing to false is reflected in the state`() {
        systemUnderTest = prepareViewModel(mapOf(SettingsOption.DARK_MODE to true))

        systemUnderTest.onSwitchValueChange(SettingsOption.DARK_MODE, false)

        val result = systemUnderTest.settingsFlow.value
        expectThat(result[SettingsOption.DARK_MODE]).isFalse()
    }

    @Test
    fun `The changed value is persisted`() {
        val repository = InMemorySettingsRepository()
        systemUnderTest = prepareViewModel(mapOf(SettingsOption.DARK_MODE to true), repository)

        systemUnderTest.onSwitchValueChange(SettingsOption.DARK_MODE, true)

        val result = repository.getBoolean(SettingsOption.DARK_MODE)
        expectThat(result).isTrue()
    }
}

internal fun prepareViewModel(
    defaultSettings: Map<SettingsOption, Boolean> = mapOf(),
    repository: Repository = InMemorySettingsRepository(),
): SettingsViewModel {
    val settingsChanger = SettingsChanger(repository, defaultSettings)
    return SettingsViewModel(settingsChanger)
}
