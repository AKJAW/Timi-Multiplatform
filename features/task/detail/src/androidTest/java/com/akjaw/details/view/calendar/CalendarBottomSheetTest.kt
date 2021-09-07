package com.akjaw.details.view.calendar

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeRight
import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.details.presenter.calendar.CalendarDaysCalculator
import com.akjaw.details.presenter.calendar.CalendarViewModel
import com.soywiz.klock.DateTime
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO change injection after Refactor from hilt to koin
class CalendarBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var viewModel: CalendarViewModel

    @Before
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
        givenCurrentMonthIs(6)
        viewModel = CalendarViewModel(
            Dispatchers.Unconfined,
            timestampProviderStub,
            CalendarDaysCalculator()
        )
        composeTestRule.setContent {
            CalendarBottomSheet(viewModel)
        }
    }

    @Test
    fun theMonthNameIsShown() {
        composeTestRule.onNodeWithText("June").assertIsDisplayed()
    }

    @Test
    fun theMonthDaysAreShown() {
        val days = listOf(31) + (1..30) + (1..11)
        assertDaysDisplayed(days)
    }

    @Test
    fun swipingRightChangesToThePreviousMonth() {
        composeTestRule.onRoot().performGesture {
            swipeRight()
        }

        composeTestRule.onNodeWithText("May").assertIsDisplayed()
    }

    private fun assertDaysDisplayed(days: List<Int>) {
        val daysCountMap = days.groupingBy { it }.eachCount()
        daysCountMap.forEach { (day, count) ->
            composeTestRule.onAllNodesWithText("$day").assertCountEquals(count)
        }
    }

    private fun givenCurrentMonthIs(monthNumber: Int) {
        timestampProviderStub.currentMilliseconds = DateTime(
            year = 2021,
            month = monthNumber,
            day = 1
        ).unixMillisLong
    }
}

class TimestampProviderStub : TimestampProvider {

    var currentMilliseconds: Long = 0

    override fun getMilliseconds(): TimestampMilliseconds =
        currentMilliseconds.toTimestampMilliseconds()
}
