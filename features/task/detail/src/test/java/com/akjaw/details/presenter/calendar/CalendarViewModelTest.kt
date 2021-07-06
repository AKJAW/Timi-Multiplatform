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
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class CalendarViewModelTest {

    companion object {
        private val JUNE_DATE_TIME = DateTime.createAdjusted(
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
        timestampProviderStub.value = JUNE_DATE_TIME.unixMillisLong.toTimestampMilliseconds()
        systemUnderTest = createCalendarViewModel(testCoroutineDispatcher, timestampProviderStub)
    }

    @Test
    fun `Initially the system timestamp is used for the month name`() = runBlocking {
        systemUnderTest.viewState.test {
            expectThat(expectItem().monthName).isEqualTo("July")
        }
    }

    @Test
    fun `Initially the system timestamp is used for the month days`() = runBlocking {
        systemUnderTest.viewState.test {
            expectThat(expectItem().dayRows).isEqualTo(
                listOf(
                    listOf(28..30, 1..4).toDays(),
                    listOf(5..11).toDays(),
                    listOf(12..18).toDays(),
                    listOf(19..25).toDays(),
                    listOf(26..31, 1..6).toDays(),
                )
            )
        }
    }

    @Test
    fun `Changing to next month correctly changes the month name`() = runBlocking {
        systemUnderTest.changeToNextMonth()

        systemUnderTest.viewState.test {
            expectThat(expectItem().monthName).isEqualTo("August")
        }
    }

    @Test
    fun `Changing to previous month correctly changes the month name`() = runBlocking {
        systemUnderTest.changeToPreviousMonth()

        systemUnderTest.viewState.test {
            expectThat(expectItem().monthName).isEqualTo("June")
        }
    }

    fun createCalendarViewModel(
        backgroundDispatcher: CoroutineDispatcher,
        timestampProvider: TimestampProvider,
    ) = CalendarViewModel(
        backgroundDispatcher = backgroundDispatcher,
        timestampProvider = timestampProvider,
        calendarDaysCalculator = CalendarDaysCalculator()
    )
}
