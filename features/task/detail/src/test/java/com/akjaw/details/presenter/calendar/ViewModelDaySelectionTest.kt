package com.akjaw.details.presenter.calendar

import app.cash.turbine.test
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.details.helper.TimestampProviderStub
import com.akjaw.details.presentation.calendar.CalendarViewModel
import com.akjaw.details.presentation.calendar.CalendarViewState
import com.akjaw.details.presentation.calendar.DayViewState
import com.soywiz.klock.DateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
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

    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeEach
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
        timestampProviderStub.value = SEPTEMBER_DATE_TIME.unixMillisLong.toTimestampMilliseconds()
        systemUnderTest = createCalendarViewModel(timestampProviderStub)
    }

    @Nested
    inner class SelectingSingleDay {

        @Test
        fun `Selecting a days marks it`() = runBlocking {
            val dayViewStateToSelect = DayViewState(21, 9, 2021)

            systemUnderTest.selectDay(dayViewStateToSelect)

            systemUnderTest.viewState.test {
                val resultingDay = awaitItem().findDay(CURRENT_MONTH_INDEX, dayViewStateToSelect)

                expectThat(resultingDay).isEqualTo(
                    dayViewStateToSelect.copy(isSelected = true)
                )
            }
        }

        @Test
        fun `Selecting the same day twice unmarks it`() = runBlocking {
            val dayViewStateToUnselect = DayViewState(21, 9, 2021)
            systemUnderTest.selectDay(dayViewStateToUnselect)

            systemUnderTest.selectDay(dayViewStateToUnselect)

            systemUnderTest.viewState.test {
                val resultingDay = awaitItem().findDay(CURRENT_MONTH_INDEX, dayViewStateToUnselect)

                expectThat(resultingDay).isEqualTo(
                    dayViewStateToUnselect.copy(isSelected = false)
                )
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

                expectThat(selectedDays).hasSize(1)
                expectThat(selectedDays.first()).isEqualTo(
                    lastSelectedDay.copy(isSelected = true)
                )
            }
        }

        @Test
        fun `Selecting a day in the previous month works`() = runBlocking {
            val dayViewStateToSelect = DayViewState(21, 8, 2021)

            systemUnderTest.selectDay(dayViewStateToSelect)

            systemUnderTest.viewState.test {
                val resultingDay = awaitItem().findDay(PREVIOUS_MONTH_INDEX, dayViewStateToSelect)

                expectThat(resultingDay).isEqualTo(
                    dayViewStateToSelect.copy(isSelected = true)
                )
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

                expectThat(allSelectedDays).hasSize(1)
                expectThat(allSelectedDays.first()).isEqualTo(
                    lastSelectedDay.copy(isSelected = true)
                )
            }
        }
    }

    private fun CalendarViewState.findDay(monthIndex: Int, day: DayViewState): DayViewState? {
        val rows = months[monthIndex].calendarDayRows
        return rows.flatten().find { it.day == day.day && it.month == day.month }
    }
}
