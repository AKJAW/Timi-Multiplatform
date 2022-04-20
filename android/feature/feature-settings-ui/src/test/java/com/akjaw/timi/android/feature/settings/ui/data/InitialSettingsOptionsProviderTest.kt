package com.akjaw.timi.android.feature.settings.ui.data

import com.akjaw.timi.android.feature.settings.ui.domain.BooleanSettingsOption
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class InitialSettingsOptionsProviderTest {

    private val settingsRepository: SettingsRepository = mockk {
        every { getBoolean(any()) } returns true
    }
    private lateinit var systemUnderTest: InitialSettingsOptionsProvider

    @BeforeEach
    fun setUp() {
        systemUnderTest = InitialSettingsOptionsProvider(settingsRepository)
    }

    @Test
    fun `Returns the correct options`() {
        val result = systemUnderTest.get()

        expectThat(result).isEqualTo(
            mapOf(
                BooleanSettingsOption.DARK_MODE to true
            )
        )
    }
}
