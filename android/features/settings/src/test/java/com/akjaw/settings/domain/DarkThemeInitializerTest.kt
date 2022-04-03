package com.akjaw.settings.domain

import com.akjaw.settings.data.InMemorySettingsRepository
import com.akjaw.settings.data.SystemDarkModeProvider
import com.akjaw.settings.presentation.DarkModeThemeStateUpdater
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isTrue

internal class DarkThemeInitializerTest {

    private val settingsRepository = InMemorySettingsRepository()
    private val systemDarkModeProvider: SystemDarkModeProvider = mockk()
    private val darkModeThemeStateUpdater: DarkModeThemeStateUpdater = mockk(relaxed = true)
    private lateinit var systemUnderTest: DarkThemeInitializer

    @BeforeEach
    fun setUp() {
        systemUnderTest = DarkThemeInitializer(
            settingsRepository = settingsRepository,
            systemDarkModeProvider = systemDarkModeProvider,
            darkModeThemeStateUpdater = darkModeThemeStateUpdater
        )
    }

    @Test
    fun `The UI theme is changed to the persisted option`() {
        settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, true)

        systemUnderTest.initialize()

        val persistedDarkMode = settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
        verify {
            darkModeThemeStateUpdater.changeDarkModeValue(persistedDarkMode)
        }
    }

    @Test
    fun `Given the option is not yet persisted then the initializer persists the system value`() {
        every { systemDarkModeProvider.isDarkModeEnabled() } returns true

        systemUnderTest.initialize()

        val persistedDarkMode = settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
        expectThat(persistedDarkMode).isTrue()
    }

    @Test
    fun `Given the option is already persisted then it is not persisted again`() {
        settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, false)

        systemUnderTest.initialize()

        verify(exactly = 0) {
            systemDarkModeProvider.isDarkModeEnabled()
        }
    }
}
