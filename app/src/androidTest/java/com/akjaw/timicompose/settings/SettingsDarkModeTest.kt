package com.akjaw.timicompose.settings

import android.content.Context
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.captureToImage
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

// TODO extract a verifier and a robot
class SettingsDarkModeTest {

    private lateinit var bottomNavRobot: BottomNavRobot

    @get:Rule
    val composeTestRule: ActivityComposeTestRule = createAndroidComposeRule()

    private val darkModeText by lazy {
        composeTestRule.activity.getString(R.string.boolean_dark_mode)
    }

    @Before
    fun setUp() {
        bottomNavRobot = BottomNavRobot(composeTestRule)
    }

    @After
    fun tearDown() {
        val preferencesKey = SharedPreferencesKeys.settings
        composeTestRule.activity.applicationContext.getSharedPreferences(
            preferencesKey,
            Context.MODE_PRIVATE
        ).edit().clear().commit()
    }

    @Test
    fun changingDarkSettingUpdatesTheBackground() {
        bottomNavRobot.navigateToSettings()
        assertDarkModeDisabled()

        composeTestRule.onNodeWithText(darkModeText).performClick()

        composeTestRule.mainClock.advanceTimeBy(500)
        assertDarkModeEnabled()
    }

    @Test
    fun changingDarkSettingUpdatesTheSwitch() {
        bottomNavRobot.navigateToSettings()
        composeTestRule.onNodeWithText(darkModeText).onChild().assertIsOff()

        composeTestRule.onNodeWithText(darkModeText).performClick()

        composeTestRule.mainClock.advanceTimeBy(500)
        composeTestRule.onNodeWithText(darkModeText).onChild().assertIsOn()
    }

    @Test
    fun theDarkSettingIsPersisted() {
        bottomNavRobot.navigateToSettings()
        composeTestRule.onNodeWithText(darkModeText).performClick()
        composeTestRule.mainClock.advanceTimeBy(500)

        composeTestRule.activityRule.scenario.recreate()

        composeTestRule.onNodeWithText(darkModeText).onChild().assertIsOn()
        assertDarkModeEnabled()
    }

    private fun assertDarkModeEnabled() {
        assertFirstPixelColor(
            node = composeTestRule.onNodeWithText(darkModeText),
            expectedHexColor = "ff121212"
        )
    }

    private fun assertDarkModeDisabled() {
        assertFirstPixelColor(
            node = composeTestRule.onNodeWithText(darkModeText),
            expectedHexColor = "ffffffff"
        )
    }

    private fun assertFirstPixelColor(node: SemanticsNodeInteraction, expectedHexColor: String) {
        val imageBitmap = node.captureToImage()
        val color = imageBitmap.asAndroidBitmap().getPixel(0, 0)
        val actualHexColor = Integer.toHexString(color)
        try {
            assert(actualHexColor == expectedHexColor)
        } catch (e: Throwable) {
            throw AssertionError("Expected color: $expectedHexColor but was $actualHexColor")
        }
    }
}
