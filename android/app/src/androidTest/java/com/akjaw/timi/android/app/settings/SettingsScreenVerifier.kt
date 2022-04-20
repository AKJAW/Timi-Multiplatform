package com.akjaw.timi.android.app.settings

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithText
import com.akjaw.timi.android.app.ActivityComposeTestRule
import com.akjaw.timi.android.feature.settings.ui.R

class SettingsScreenVerifier(private val composeTestRule: ActivityComposeTestRule) {

    private val darkModeText by lazy {
        composeTestRule.activity.getString(R.string.boolean_dark_mode)
    }

    fun assertDarkModeSwitch(isSet: Boolean) {
        val switch = composeTestRule.onNodeWithText(darkModeText).onChild()
        if (isSet) {
            switch.assertIsOn()
        } else {
            switch.assertIsOff()
        }
    }
}
