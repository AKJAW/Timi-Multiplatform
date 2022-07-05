package com.akjaw.timi.android.feature.task.detail.ui.presenter

import app.cash.turbine.test
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.createCalendarViewModel
import com.akjaw.timi.android.feature.task.detail.ui.helper.TimestampProviderStub
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.DayViewState
import com.soywiz.klock.DateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
internal class ViewModelMonthNameAndDaysTest {

    companion object {
        private val JULY_DATE_TIME = DateTime.createAdjusted(
            year = 2021,
            month = 7,
            day = 5
        )

        private const val CURRENT_MONTH_INDEX = CalendarViewModel.CURRENT_MONTH_INDEX
    }

    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeEach
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
        timestampProviderStub.value = JULY_DATE_TIME.unixMillisLong.toTimestampMilliseconds()
        systemUnderTest = createCalendarViewModel(timestampProviderStub)
    }

    @Nested
    inner class MonthName {

        @Test
        fun `The current month name is at the 40th index`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(awaitItem().months[CURRENT_MONTH_INDEX].monthName).isEqualTo("July")
            }
        }

        @Test
        fun `The previous month name is at the 39th index`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(awaitItem().months[39].monthName).isEqualTo("June")
            }
        }

        @Test
        fun `The next month name is at the 41th index`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(awaitItem().months[41].monthName).isEqualTo("August")
            }
        }

        @Test
        fun `The next year month name the year number in it`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(awaitItem().months[46].monthName).isEqualTo("January 2022")
            }
        }

        @Test
        fun `The previous year month name the year number in it`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(awaitItem().months[33].monthName).isEqualTo("December 2020")
            }
        }
    }

    @Nested
    inner class MonthDays {

        @Test
        fun `The current month first day is correct`() = runBlocking {
            systemUnderTest.viewState.test {
                expect {
                    val rows = awaitItem().months[CURRENT_MONTH_INDEX].calendarDayRows
                    val firstDay = rows.first().first()
                    that(firstDay).isEqualTo(DayViewState(28, 6, 2021))
                }
            }
        }

        @Test
        fun `The current month last day is correct`() = runBlocking {
            systemUnderTest.viewState.test {
                expect {
                    val rows = awaitItem().months[CURRENT_MONTH_INDEX].calendarDayRows
                    val firstDay = rows.last().last()
                    that(firstDay).isEqualTo(DayViewState(8, 8, 2021))
                }
            }
        }
    }
}
