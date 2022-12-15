package com.akjaw.timi.android.feature.settings.ui.presentation

import com.akjaw.timi.android.feature.settings.ui.domain.BooleanSettingsOption
import com.akjaw.timi.android.feature.settings.ui.domain.SettingsChanger
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class SettingsViewModelTest {

    private val settingsChanger: SettingsChanger = mockk(relaxed = true)
    private val darkModeThemeStateUpdater: DarkModeThemeStateUpdater = mockk(relaxed = true)
    private lateinit var systemUnderTest: SettingsViewModel

    @BeforeEach
    fun setUp() {
        systemUnderTest = SettingsViewModel(settingsChanger, darkModeThemeStateUpdater)
    }

    @Test
    fun `The settings changer value is used`() {
        val initialValue = mapOf(BooleanSettingsOption.DARK_MODE to true)
        every { settingsChanger.booleanSettings } returns MutableStateFlow(initialValue)
        val systemUnderTest = SettingsViewModel(settingsChanger, darkModeThemeStateUpdater)

        val result = systemUnderTest.booleanOptionsFlow.value

        expectThat(result).isEqualTo(initialValue)
    }

    @Test
    fun `Calls the setting changer on value change`() {
        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DARK_MODE, false)

        verify { settingsChanger.changeBooleanSetting(BooleanSettingsOption.DARK_MODE, false) }
    }

    @Test
    fun `If the dark mode is changed the theme state is also changed`() {
        systemUnderTest.onSwitchValueChange(BooleanSettingsOption.DARK_MODE, true)

        verify { darkModeThemeStateUpdater.changeDarkModeValue(true) }
    }
}
