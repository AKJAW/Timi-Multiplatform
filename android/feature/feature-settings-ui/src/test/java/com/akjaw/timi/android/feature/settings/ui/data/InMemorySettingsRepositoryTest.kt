package com.akjaw.timi.android.feature.settings.ui.data

import com.akjaw.timi.android.feature.settings.ui.domain.BooleanSettingsOption
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class InMemorySettingsRepositoryTest : SettingsRepositoryContractTest() {

    private lateinit var systemUnderTest: InMemorySettingsRepository

    @BeforeEach
    fun setUp() {
        systemUnderTest = InMemorySettingsRepository()
    }

    @Test
    override fun `Given the value is not persisted the repository does not contain the value`() {
        val result = systemUnderTest.containsOption(BooleanSettingsOption.DARK_MODE)

        expectThat(result).isFalse()
    }

    @Test
    override fun `Given the value is persisted the repository contains the value`() {
        systemUnderTest.setBoolean(BooleanSettingsOption.DARK_MODE, true)

        val result = systemUnderTest.containsOption(BooleanSettingsOption.DARK_MODE)

        expectThat(result).isTrue()
    }

    @Test
    override fun `The default value is false`() {
        val result = systemUnderTest.getBoolean(BooleanSettingsOption.DARK_MODE)

        expectThat(result).isFalse()
    }

    @Test
    override fun `The saved setting is persisted`() {
        systemUnderTest.setBoolean(BooleanSettingsOption.DARK_MODE, true)

        val result = systemUnderTest.getBoolean(BooleanSettingsOption.DARK_MODE)

        expectThat(result).isTrue()
    }
}
