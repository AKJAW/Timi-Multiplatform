package com.akjaw.timi.android.feature.settings.ui.presentation

import com.akjaw.timi.android.core.view.theme.ThemeState

class DarkModeThemeStateUpdater {

    fun changeDarkModeValue(newValue: Boolean) {
        ThemeState.isDarkTheme.value = newValue
    }
}
