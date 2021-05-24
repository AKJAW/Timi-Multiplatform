package com.akjaw.timicompose.settings

import android.content.Context
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.core.common.data.persistance.SharedPreferencesKeys
import com.akjaw.settings.R
import com.akjaw.timicompose.ActivityComposeTestRule
import com.akjaw.timicompose.BottomNavRobot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsDarkModeTest {

    private lateinit var bottomNavRobot: BottomNavRobot

    @get:Rule
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    @Before
    fun setUp() {
        bottomNavRobot = BottomNavRobot(composeTestRule)
    }

    // TODO revise this test when it will be possible to assert colors
    @Test
    fun changingDarkSettingUpdatesTheSwitch() {
        bottomNavRobot.navigateToSettings()
        val darkModeText = composeTestRule.activity.getString(R.string.boolean_dark_mode)

        composeTestRule.onNodeWithText(darkModeText).performClick()

        composeTestRule.mainClock.advanceTimeBy(500)
        composeTestRule.onNodeWithText(darkModeText).onChild().assertIsOn()
    }

    @Test
    fun theDarkSettingIsPersisted() {
        bottomNavRobot.navigateToSettings()
        val darkModeText = composeTestRule.activity.getString(R.string.boolean_dark_mode)
        composeTestRule.onNodeWithText(darkModeText).performClick()
        composeTestRule.mainClock.advanceTimeBy(500)

        composeTestRule.activityRule.scenario.recreate()

        composeTestRule.onNodeWithText(darkModeText).onChild().assertIsOn()
    }

    @After
    fun tearDown() {
        val preferencesKey = SharedPreferencesKeys.settings
        composeTestRule.activity.applicationContext.getSharedPreferences(
            preferencesKey,
            Context.MODE_PRIVATE
        ).edit().clear().commit()
    }
}
