package com.akjaw.details.presenter.calendar

import app.cash.turbine.test
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.soywiz.klock.DateTime
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FirstCalendarRowCalculator {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeEach
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
    }

    @Test
    fun `Correctly calculates the first row when month first day is on tuesday`() = runBlocking {
        val monthWithTuesdayStart = DateTime.createAdjusted(2021, 7, 1)
        timestampProviderStub.value =
            monthWithTuesdayStart.unixMillisLong.toTimestampMilliseconds()

        systemUnderTest = createCalendarViewModel(testCoroutineDispatcher, timestampProviderStub)

        systemUnderTest.viewState.test {
            val expectedDays = listOf(28, 29, 30, 1, 2, 3, 4).map { Day(it.toString()) }
            expectThat(expectItem().dayRows.first()).isEqualTo(expectedDays)
        }
    }

    @Test
    fun `Correctly calculates the first row when month first day is on monday`() = runBlocking {
        val monthWithMondayStart = DateTime.createAdjusted(2021, 11, 1)
        timestampProviderStub.value =
            monthWithMondayStart.unixMillisLong.toTimestampMilliseconds()

        systemUnderTest = createCalendarViewModel(testCoroutineDispatcher, timestampProviderStub)

        systemUnderTest.viewState.test {
            val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7).map { Day(it.toString()) }
            expectThat(expectItem().dayRows.first()).isEqualTo(expectedDays)
        }
    }

    @Test
    fun `Correctly calculates the first row when month first day is on sunday`() = runBlocking {
        val monthWithMondayStart = DateTime.createAdjusted(2021, 8, 1)
        timestampProviderStub.value =
            monthWithMondayStart.unixMillisLong.toTimestampMilliseconds()

        systemUnderTest = createCalendarViewModel(testCoroutineDispatcher, timestampProviderStub)

        systemUnderTest.viewState.test {
            val expectedDays = listOf(26, 27, 28, 29, 30, 31, 1).map { Day(it.toString()) }
            expectThat(expectItem().dayRows.first()).isEqualTo(expectedDays)
        }
    }

    @Test
    fun `Correctly calculates the first row of days when previous month was leap february`() = runBlocking {
        val leapYearMarchDateTime = DateTime.createAdjusted(2020, 3, 1)
        timestampProviderStub.value =
            leapYearMarchDateTime.unixMillisLong.toTimestampMilliseconds()

        systemUnderTest = createCalendarViewModel(testCoroutineDispatcher, timestampProviderStub)

        systemUnderTest.viewState.test {
            val expectedDays = listOf(24, 25, 26, 27, 28, 29, 1).map { Day(it.toString()) }
            expectThat(expectItem().dayRows.first()).isEqualTo(expectedDays)
        }
    }

    // August 2021
    // May 2021
    // November 2021
}
