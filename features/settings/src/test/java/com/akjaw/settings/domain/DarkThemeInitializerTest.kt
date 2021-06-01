package com.akjaw.settings.domain

import com.akjaw.core.common.view.theme.ThemeState
import com.akjaw.settings.data.InMemorySettingsRepository
import com.akjaw.settings.data.SystemDarkModeProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

internal class DarkThemeInitializerTest {

    private val settingsRepository = InMemorySettingsRepository()
    private val systemDarkModeProvider: SystemDarkModeProvider = mockk()
    private lateinit var systemUnderTest: DarkThemeInitializer

    @BeforeEach
    fun setUp() {
        systemUnderTest = DarkThemeInitializer(settingsRepository, systemDarkModeProvider)
    }

    @Test
    fun `The UI theme is changed to the persisted option`() {
        settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, true)

        systemUnderTest.initialize()

        val persistedDarkMode = settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
        expectThat(ThemeState.isDarkTheme.value).isEqualTo(persistedDarkMode)
    }

    @Nested
    inner class DarkModeNotYetPersisted {

        @Test
        fun `Given the option is not yet persisted then the system value is persisted`() {
            every { systemDarkModeProvider.isDarkModeEnabled() } returns true

            systemUnderTest.initialize()

            val persistedDarkMode = settingsRepository.getBoolean(BooleanSettingsOption.DARK_MODE)
            expectThat(persistedDarkMode).isTrue()
        }
    }

    @Nested
    inner class DarkModeAlreadyPersisted {

        @Test
        fun `Given the option is already persisted then the system value is retrieved`() {
            settingsRepository.setBoolean(BooleanSettingsOption.DARK_MODE, false)

            systemUnderTest.initialize()

            verify(exactly = 0) {
                systemDarkModeProvider.isDarkModeEnabled()
            }
        }
    }
}
