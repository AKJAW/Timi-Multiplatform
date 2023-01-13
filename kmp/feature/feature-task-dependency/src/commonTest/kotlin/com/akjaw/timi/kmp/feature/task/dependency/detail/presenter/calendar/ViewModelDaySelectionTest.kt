package com.akjaw.timi.kmp.feature.task.dependency.detail.presenter.calendar

import app.cash.turbine.test
import com.akjaw.timi.kmp.core.test.time.StubTimestampProvider
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.CalendarViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.DayViewState
import com.akjaw.timi.kmp.feature.task.dependency.createCalendarViewModel
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import com.soywiz.klock.DateTime
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class ViewModelDaySelectionTest {

    companion object {
        private val SEPTEMBER_DATE_TIME = DateTime.createAdjusted(
            year = 2021,
            month = 9,
            day = 21
        )

        private const val CURRENT_MONTH_INDEX = CalendarViewModel.CURRENT_MONTH_INDEX
        private const val PREVIOUS_MONTH_INDEX = CalendarViewModel.CURRENT_MONTH_INDEX - 1
    }

    private lateinit var stubTimestampProvider: StubTimestampProvider
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeTest
    fun setUp() {
        stubTimestampProvider = StubTimestampProvider()
        stubTimestampProvider.setValue(SEPTEMBER_DATE_TIME.unixMillisLong)
        systemUnderTest = createCalendarViewModel(stubTimestampProvider)
    }

    @Test
    fun `Selecting a days marks it`() = runBlocking {
        val dayViewStateToSelect = DayViewState(21, 9, 2021)

        systemUnderTest.selectDay(dayViewStateToSelect)

        systemUnderTest.viewState.test {
            val resultingDay = awaitItem().findDay(CURRENT_MONTH_INDEX, dayViewStateToSelect)

            resultingDay shouldBe dayViewStateToSelect.copy(isSelected = true)
        }
    }

    @Test
    fun `Selecting the same day twice unmarks it`() = runBlocking {
        val dayViewStateToUnselect = DayViewState(21, 9, 2021)
        systemUnderTest.selectDay(dayViewStateToUnselect)

        systemUnderTest.selectDay(dayViewStateToUnselect)

        systemUnderTest.viewState.test {
            val resultingDay = awaitItem().findDay(CURRENT_MONTH_INDEX, dayViewStateToUnselect)

            resultingDay shouldBe dayViewStateToUnselect.copy(isSelected = false)
        }
    }

    @Test
    fun `Only the last selected day is marked`() = runBlocking {
        val lastSelectedDay = DayViewState(21, 9, 2021)
        systemUnderTest.selectDay(DayViewState(20, 9, 2021))
        systemUnderTest.selectDay(DayViewState(15, 9, 2021))

        systemUnderTest.selectDay(lastSelectedDay)

        systemUnderTest.viewState.test {
            val currentMonthRows = awaitItem().months[CURRENT_MONTH_INDEX].calendarDayRows
            val selectedDays = currentMonthRows.flatten().filter { it.isSelected }

            assertSoftly {
                selectedDays shouldHaveSize 1
                selectedDays.first() shouldBe lastSelectedDay.copy(isSelected = true)
            }
        }
    }

    @Test
    fun `Selecting a day in the previous month works`() = runBlocking {
        val dayViewStateToSelect = DayViewState(21, 8, 2021)

        systemUnderTest.selectDay(dayViewStateToSelect)

        systemUnderTest.viewState.test {
            val resultingDay = awaitItem().findDay(PREVIOUS_MONTH_INDEX, dayViewStateToSelect)

            resultingDay shouldBe dayViewStateToSelect.copy(isSelected = true)
        }
    }

    @Test
    fun `Selecting two days in different months only marks the last one`() = runBlocking {
        val lastSelectedDay = DayViewState(21, 9, 2021)
        systemUnderTest.selectDay(DayViewState(20, 8, 2021))

        systemUnderTest.selectDay(lastSelectedDay)

        systemUnderTest.viewState.test {
            val allRows = awaitItem().months.flatMap { it.calendarDayRows }
            val allSelectedDays = allRows.flatMap { row ->
                row.filter { day -> day.isSelected }
            }

            assertSoftly {
                allSelectedDays shouldHaveSize 1
                allSelectedDays.first() shouldBe lastSelectedDay.copy(isSelected = true)
            }
        }
    }

    private fun CalendarViewState.findDay(monthIndex: Int, day: DayViewState): DayViewState? {
        val rows = months[monthIndex].calendarDayRows
        return rows.flatten().find { it.day == day.day && it.month == day.month }
    }
}
