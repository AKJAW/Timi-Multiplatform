package com.akjaw.settings.presentation

import com.akjaw.timi.android.core.view.theme.ThemeState

class DarkModeThemeStateUpdater {

    fun changeDarkModeValue(newValue: Boolean) {
        ThemeState.isDarkTheme.value = newValue
    }
}
