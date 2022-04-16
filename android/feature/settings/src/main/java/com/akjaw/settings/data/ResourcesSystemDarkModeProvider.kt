package com.akjaw.settings.data

import android.content.Context
import android.content.res.Configuration

class ResourcesSystemDarkModeProvider(
    private val applicationContext: Context
) : SystemDarkModeProvider {

    override fun isDarkModeEnabled(): Boolean {
        return applicationContext.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}
