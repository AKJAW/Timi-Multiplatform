package com.akjaw.details.view.calendar

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalendarBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        givenCurrentMonthIs(6)
        composeTestRule.setContent { CalendarBottomSheet() }
    }

    @Test
    fun theMonthNameIsShown() {
        composeTestRule.onNodeWithText("June").assertIsDisplayed()
    }

    @Test
    fun theMonthDaysAreShown() {
        composeTestRule.onRoot().printToLog("Aaa")
        val days = listOf(31) + (1..30) + (1..4)
        assertDaysDisplayed(days)
    }

    private fun assertDaysDisplayed(days: List<Int>) {
        val daysCountMap = days.groupingBy { it }.eachCount()
        daysCountMap.forEach { (day, count) ->
            composeTestRule.onAllNodesWithText("$day").assertCountEquals(count)
        }
    }

    private fun givenCurrentMonthIs(monthNumber: Int) {
        /* Placeholder */
    }
}
