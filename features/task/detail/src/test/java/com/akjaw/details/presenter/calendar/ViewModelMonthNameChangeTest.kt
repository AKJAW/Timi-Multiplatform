package com.akjaw.details.presenter.calendar

import app.cash.turbine.test
import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.details.helper.TimestampProviderStub
import com.soywiz.klock.DateTime
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class ViewModelMonthNameChangeTest {

    companion object {
        private val JULY_DATE_TIME = DateTime.createAdjusted(
            year = 2021,
            month = 7,
            day = 5
        )
    }

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeEach
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
        timestampProviderStub.value = JULY_DATE_TIME.unixMillisLong.toTimestampMilliseconds()
        systemUnderTest = createCalendarViewModel(testCoroutineDispatcher, timestampProviderStub)
    }

    @Nested
    inner class MonthName {

        @Test
        fun `The current month name is at the 40th index`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().months[40].monthName).isEqualTo("July")
            }
        }

        @Test
        fun `The previous month name is at the 39th index`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().months[39].monthName).isEqualTo("June")
            }
        }

        @Test
        fun `The next month name is at the 41th index`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().months[41].monthName).isEqualTo("August")
            }
        }

        @Test
        fun `The next year month name the year number in it`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().months[46].monthName).isEqualTo("January 2022")
            }
        }

        @Test
        fun `The previous year month name the year number in it`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().months[33].monthName).isEqualTo("December 2020")
            }
        }
    }

    @Nested
    inner class MonthDays {

        @Test
        fun `The current month first day is correct`() = runBlocking {
            systemUnderTest.viewState.test {
                expect {
                    val rows = expectItem().months[40].calendarDayRows
                    val firstDay = rows.first().first()
                    that(firstDay).isEqualTo(CalendarDay(28, 6, 2021))
                }
            }
        }

        @Test
        fun `The current month last day is correct`() = runBlocking {
            systemUnderTest.viewState.test {
                expect {
                    val rows = expectItem().months[40].calendarDayRows
                    val firstDay = rows.last().last()
                    that(firstDay).isEqualTo(CalendarDay(8, 8, 2021))
                }
            }
        }
    }

    private fun createCalendarViewModel(
        backgroundDispatcher: CoroutineDispatcher,
        timestampProvider: TimestampProvider,
    ) = CalendarViewModel(
        backgroundDispatcher = backgroundDispatcher,
        timestampProvider = timestampProvider,
        calendarDaysCalculator = CalendarDaysCalculator()
    )

    private fun List<Iterable<Int>>.toDays(): List<CalendarDay> =
        this.flatMap { range -> range.map { number -> CalendarDay(number) } }
}
