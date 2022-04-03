package com.akjaw.settings.presentation

import com.akjaw.core.common.view.theme.ThemeState

class DarkModeThemeStateUpdater {

    fun changeDarkModeValue(newValue: Boolean) {
        ThemeState.isDarkTheme.value = newValue
    }
}
