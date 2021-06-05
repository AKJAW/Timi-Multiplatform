package com.akjaw.settings

import com.akjaw.core.common.view.theme.ThemeState
import com.akjaw.settings.data.InMemorySettingsRepository
import com.akjaw.settings.data.InitialSettingsOptionsProvider
import com.akjaw.settings.data.SettingsRepository
import com.akjaw.settings.domain.BooleanSettingsOption
import com.akjaw.settings.domain.SettingsChanger
import com.akjaw.settings.presentation.DarkModeThemeStateUpdater
import com.akjaw.settings.presentation.SettingsViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class ChangeBooleanSettingIntegrationTest {

    private lateinit var systemUnderTest: SettingsViewModel

    @Test
    fun `Changing to true is reflected in the state`() {
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DARK_MODE to false))

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DARK_MODE, true)

        val result = systemUnderTest.booleanOptionsFlow.value
        expectThat(result[BooleanSettingsOption.DARK_MODE]).isTrue()
    }

    @Test
    fun `Changing to false is reflected in the state`() {
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DARK_MODE to true))

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DARK_MODE, false)

        val result = systemUnderTest.booleanOptionsFlow.value
        expectThat(result[BooleanSettingsOption.DARK_MODE]).isFalse()
    }

    @Test
    fun `The changed value is persisted`() {
        val repository = InMemorySettingsRepository()
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DARK_MODE to true), repository)

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DARK_MODE, true)

        val result = repository.getBoolean(BooleanSettingsOption.DARK_MODE)
        expectThat(result).isTrue()
    }

    @Test
    fun `Changing the dark mode also changes the theme state`() {
        systemUnderTest = prepareViewModel(mapOf(BooleanSettingsOption.DARK_MODE to false))

        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DARK_MODE, true)

        expectThat(ThemeState.isDarkTheme.value).isTrue()
    }
}

internal fun prepareViewModel(
    initialBooleanSettings: Map<BooleanSettingsOption, Boolean> = mapOf(),
    settingsRepository: SettingsRepository = InMemorySettingsRepository(),
    darkModeThemeStateUpdater: DarkModeThemeStateUpdater = DarkModeThemeStateUpdater()
): SettingsViewModel {
    val initialSettingsOptionsProvider: InitialSettingsOptionsProvider = mockk {
        every { this@mockk.get() } returns initialBooleanSettings
    }
    val settingsChanger = SettingsChanger(settingsRepository, initialSettingsOptionsProvider)
    return SettingsViewModel(settingsChanger, darkModeThemeStateUpdater)
}
