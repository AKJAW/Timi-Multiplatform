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
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
    inner class Initial {

        @Test
        fun `The previous month name has the correct name`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().previousMonth.monthName).isEqualTo("June")
            }
        }

        @Test
        fun `The current month name has the correct name`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().monthName).isEqualTo("July")
            }
        }

        @Test
        fun `The next month name has the correct name`() = runBlocking {
            systemUnderTest.viewState.test {
                expectThat(expectItem().nextMonth.monthName).isEqualTo("August")
            }
        }
    }

    @Nested
    inner class ChangingToPrevious {

        @Test
        fun `Correctly changes the previous month name`() = runBlocking {
            systemUnderTest.changeToPreviousMonth()

            systemUnderTest.viewState.test {
                expectThat(expectItem().previousMonth.monthName).isEqualTo("May")
            }
        }

        @Test
        fun `Correctly changes the current month name`() = runBlocking {
            systemUnderTest.changeToPreviousMonth()

            systemUnderTest.viewState.test {
                expectThat(expectItem().monthName).isEqualTo("June")
            }
        }

        @Test
        fun `Correctly changes the next month name`() = runBlocking {
            systemUnderTest.changeToPreviousMonth()

            systemUnderTest.viewState.test {
                expectThat(expectItem().nextMonth.monthName).isEqualTo("July")
            }
        }
    }

    @Nested
    inner class ChangingToNext {

        @Test
        fun `Correctly changes the previous month name`() = runBlocking {
            systemUnderTest.changeToNextMonth()

            systemUnderTest.viewState.test {
                expectThat(expectItem().previousMonth.monthName).isEqualTo("July")
            }
        }

        @Test
        fun `Correctly changes the current month name`() = runBlocking {
            systemUnderTest.changeToNextMonth()

            systemUnderTest.viewState.test {
                expectThat(expectItem().monthName).isEqualTo("August")
            }
        }

        @Test
        fun `Correctly changes the next month name`() = runBlocking {
            systemUnderTest.changeToNextMonth()

            systemUnderTest.viewState.test {
                expectThat(expectItem().nextMonth.monthName).isEqualTo("September")
            }
        }
    }

    @Test
    @Disabled("Disables because this test will be moved to a separate class")
    fun `Initially the system timestamp is used for the month days`() = runBlocking {
        systemUnderTest.viewState.test {
            expectThat(expectItem().calendarDayRows).isEqualTo(
                listOf(
                    listOf(28..30, 1..4).toDays(),
                    listOf(5..11).toDays(),
                    listOf(12..18).toDays(),
                    listOf(19..25).toDays(),
                    listOf(26, 27, 28, 29, 30, 31, 1).map { CalendarDay(it) },
                    listOf(2..8).toDays(),
                )
            )
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
