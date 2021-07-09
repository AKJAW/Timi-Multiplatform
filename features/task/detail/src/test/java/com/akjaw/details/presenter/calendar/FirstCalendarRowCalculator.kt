package com.akjaw.details.presenter.calendar

import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FirstCalendarRowCalculator {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Nested
    inner class FirstRow {

        @Test
        fun `Correctly calculates when current month first day is on tuesday`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(28, 29, 30, 1, 2, 3, 4).map { Day(it.toString()) }
            expectThat(result.first()).isEqualTo(expectedDays)
        }

        @Test
        fun `Correctly calculates when current month first day is on monday`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7).map { Day(it.toString()) }
            expectThat(result.first()).isEqualTo(expectedDays)
        }

        @Test
        fun `Correctly calculates when current month first day is on sunday`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 8, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(26, 27, 28, 29, 30, 31, 1).map { Day(it.toString()) }
            expectThat(result.first()).isEqualTo(expectedDays)
        }

        @Test
        fun `Correctly calculates when previous month was leap february`() {
            val monthUnderTest = DateTime.createAdjusted(2020, 3, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(24, 25, 26, 27, 28, 29, 1).map { Day(it.toString()) }
            expectThat(result.first()).isEqualTo(expectedDays)
        }
    }

    @Nested
    inner class LastRow {

        @Test
        fun `Correctly calculates when current month is on the last row`() {
            val monthUnderTest = DateTime.createAdjusted(2022, 1, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(31, 1, 2, 3, 4, 5, 6).map { Day(it.toString()) }
            expectThat(result.last()).isEqualTo(expectedDays)
        }

        @Test
        fun `Correctly calculates when next month starts the last row`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 10, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7).map { Day(it.toString()) }
            expectThat(result.last()).isEqualTo(expectedDays)
        }

        @Test
        fun `Correctly calculates when next month starts in the middle of the second to last row`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 6, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(5, 6, 7, 8, 9, 10, 11).map { Day(it.toString()) }
            expectThat(result.last()).isEqualTo(expectedDays)
        }

        @Test
        fun `Correctly calculates when next month fills last two rows`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(8, 9, 10, 11, 12, 13, 14).map { Day(it.toString()) }
            expectThat(result.last()).isEqualTo(expectedDays)
        }
    }
}
