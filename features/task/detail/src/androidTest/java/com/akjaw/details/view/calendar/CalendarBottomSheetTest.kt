package com.akjaw.details.view.calendar

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeLeft
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
    fun swipingRightChangesTheNameToPreviousMonth() {
        composeTestRule.onRoot().performGesture {
            swipeRight()
        }

        composeTestRule.onNodeWithText("May").assertIsDisplayed()
    }

    @Test
    fun swipingRightChangesTheDaysToPreviousMonth() {
        composeTestRule.onRoot().performGesture {
            swipeRight()
        }

        val days = (26..30) + (1..31) + (1..6)
        assertDaysDisplayed(days)
    }

    @Test
    fun swipingLeftChangesTheNameToNextMonth() {
        composeTestRule.onRoot().performGesture {
            swipeLeft()
        }

        composeTestRule.onNodeWithText("July").assertIsDisplayed()
    }

    @Test
    fun swipingLeftChangesTheDaysToNextMonth() {
        composeTestRule.onRoot().performGesture {
            swipeLeft()
        }

        val days = (28..30) + (1..31) + (1..8)
        assertDaysDisplayed(days)
    }

    private fun assertDaysDisplayed(days: List<Int>) {
        val currentMonthDays = composeTestRule
            .onNodeWithTag("CalendarPager")
            .onChildAt(1)
            .onChildren()
            .fetchSemanticsNodes()
            .mapNotNull { semanticsNode ->
                val textNode = semanticsNode.children.firstOrNull()
                if (textNode != null) {
                    val semanticTextProperty = textNode.config[SemanticsProperties.Text]
                    val text = semanticTextProperty.first().text
                    text.toIntOrNull()
                } else {
                    null
                }
            }
        assert(days.sorted() == currentMonthDays.sorted()) {
            """
                Current month days are incorrect
                Expected: $days
                Actual: $currentMonthDays
            """.trimIndent()
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
