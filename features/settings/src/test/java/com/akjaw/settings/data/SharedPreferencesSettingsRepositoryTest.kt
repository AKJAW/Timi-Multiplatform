package com.akjaw.settings.data

import android.content.SharedPreferences
import com.akjaw.settings.domain.BooleanSettingsOption
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse

internal class SharedPreferencesSettingsRepositoryTest : SettingsRepositoryContractTest() {

    private val sharedPreferences: SharedPreferences = mockk(relaxed = true)
    private lateinit var systemUnderTest: SharedPreferencesSettingsRepository

    @BeforeEach
    fun setUp() {
        systemUnderTest = SharedPreferencesSettingsRepository(sharedPreferences)
    }

    @Test
    override fun `The default value is false`() {
        every { sharedPreferences.getBoolean(BooleanSettingsOption.DARK_MODE.key, false) } returns false

        val result = systemUnderTest.getBoolean(BooleanSettingsOption.DARK_MODE)

        expectThat(result).isFalse()
    }

    @Test
    override fun `The saved setting is persisted`() {
        systemUnderTest.setBoolean(BooleanSettingsOption.DARK_MODE, true)

        verify { sharedPreferences.edit().putBoolean(BooleanSettingsOption.DARK_MODE.key, true).apply() }
    }
}
