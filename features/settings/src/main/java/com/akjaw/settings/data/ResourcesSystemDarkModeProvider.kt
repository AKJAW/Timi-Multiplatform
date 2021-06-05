package com.akjaw.settings.data

import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourcesSystemDarkModeProvider @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : SystemDarkModeProvider {

    override fun isDarkModeEnabled(): Boolean {
        return applicationContext.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}
