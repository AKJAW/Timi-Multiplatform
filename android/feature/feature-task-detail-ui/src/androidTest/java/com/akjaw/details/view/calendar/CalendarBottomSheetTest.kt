package com.akjaw.details.view.calendar

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.state.ToggleableState
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
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.android.feature.task.detail.ui.presentation.calendar.CalendarViewModel
import com.akjaw.timi.android.feature.task.detail.ui.view.calendar.CalendarBottomSheet
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.soywiz.klock.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO move to app?
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

    @Test
    fun clickingOnADayHighlightsIt() {
        getDayNodesOfCurrentMonth().first().config[SemanticsActions.OnClick].action?.invoke()

        composeTestRule.mainClock.advanceTimeBy(12000)
        val toggleState = getDayNodesOfCurrentMonth().first().config[SemanticsProperties.ToggleableState]
        assert(toggleState == ToggleableState.On) {
            "Expected that day is toggled but is $toggleState"
        }
    }

    private fun assertDaysDisplayed(days: List<Int>) {
        val currentMonthDays = getDayNodesOfCurrentMonth()
            .mapNotNull { semanticsNode ->
                val semanticTextProperty = semanticsNode.config[SemanticsProperties.Text]
                val text = semanticTextProperty.first().text
                text.toIntOrNull()
            }
        assert(days.sorted() == currentMonthDays.sorted()) {
            """
                Current month days are incorrect
                Expected: $days
                Actual: $currentMonthDays
            """.trimIndent()
        }
    }

    private fun getDayNodesOfCurrentMonth(): List<SemanticsNode> {
        return composeTestRule
            .onNodeWithTag("CalendarPager")
            .onChildAt(1)
            .onChildren()
            .fetchSemanticsNodes()
            .filter { semanticsNode ->
                val text = semanticsNode.config[SemanticsProperties.Text].first().text
                text.toIntOrNull() != null
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
