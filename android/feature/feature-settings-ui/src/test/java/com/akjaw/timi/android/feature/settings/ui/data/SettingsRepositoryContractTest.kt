package com.akjaw.timi.android.feature.settings.ui.data

import org.junit.jupiter.api.Test

abstract class SettingsRepositoryContractTest {

    @Test
    abstract fun `Given the value is not persisted the repository does not contain the value`()

    @Test
    abstract fun `Given the value is persisted the repository contains the value`()

    @Test
    abstract fun `The default value is false`()

    @Test
    abstract fun `The saved setting is persisted`()
}
