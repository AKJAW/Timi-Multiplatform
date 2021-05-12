package com.akjaw.settings.view

import androidx.compose.runtime.Composable
import javax.inject.Inject

internal class SettingsScreenCreatorImpl @Inject constructor() : SettingsScreenCreator {

    @Composable
    override fun Create() {
        SettingsScreen()
    }
}
