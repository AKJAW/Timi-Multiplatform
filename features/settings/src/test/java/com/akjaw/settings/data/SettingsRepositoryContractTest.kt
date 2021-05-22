package com.akjaw.settings.data

import org.junit.jupiter.api.Test

abstract class SettingsRepositoryContractTest {

    @Test
    abstract fun `The default value is false`()

    @Test
    abstract fun `The saved setting is persisted`()
}
