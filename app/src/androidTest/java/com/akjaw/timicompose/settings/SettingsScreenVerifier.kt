package com.akjaw.timicompose.settings

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithText
import com.akjaw.settings.R
import com.akjaw.timicompose.ActivityComposeTestRule

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
