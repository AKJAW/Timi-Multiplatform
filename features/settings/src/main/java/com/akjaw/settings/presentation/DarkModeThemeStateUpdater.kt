package com.akjaw.settings.presentation

import com.akjaw.core.common.view.theme.ThemeState
import javax.inject.Inject

class DarkModeThemeStateUpdater @Inject constructor() {

    fun changeDarkModeValue(newValue: Boolean) {
        ThemeState.isDarkTheme.value = newValue
    }
}
