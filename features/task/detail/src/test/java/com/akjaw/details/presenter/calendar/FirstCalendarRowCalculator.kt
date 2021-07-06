package com.akjaw.details.presenter.calendar

import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
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

    @Test
    fun `Correctly calculates the first row when month first day is on tuesday`() {
        val monthWithTuesdayStart = DateTime.createAdjusted(2021, 7, 1)

        val result = systemUnderTest.calculate(monthWithTuesdayStart)

        val expectedDays = listOf(28, 29, 30, 1, 2, 3, 4).map { Day(it.toString()) }
        expectThat(result.first()).isEqualTo(expectedDays)
    }

    @Test
    fun `Correctly calculates the first row when month first day is on monday`() {
        val monthWithMondayStart = DateTime.createAdjusted(2021, 11, 1)

        val result = systemUnderTest.calculate(monthWithMondayStart)

        val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7).map { Day(it.toString()) }
        expectThat(result.first()).isEqualTo(expectedDays)
    }

    @Test
    fun `Correctly calculates the first row when month first day is on sunday`() {
        val monthWithSundayStart = DateTime.createAdjusted(2021, 8, 1)

        val result = systemUnderTest.calculate(monthWithSundayStart)

        val expectedDays = listOf(26, 27, 28, 29, 30, 31, 1).map { Day(it.toString()) }
        expectThat(result.first()).isEqualTo(expectedDays)
    }

    @Test
    fun `Correctly calculates the first row of days when previous month was leap february`() {
        val leapYearMarchDateTime = DateTime.createAdjusted(2020, 3, 1)

        val result = systemUnderTest.calculate(leapYearMarchDateTime)

        val expectedDays = listOf(24, 25, 26, 27, 28, 29, 1).map { Day(it.toString()) }
        expectThat(result.first()).isEqualTo(expectedDays)
    }
}
