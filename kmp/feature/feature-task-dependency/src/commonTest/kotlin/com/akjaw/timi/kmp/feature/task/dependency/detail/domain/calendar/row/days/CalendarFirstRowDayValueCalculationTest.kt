package com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.row.days

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.rowDaysEqual
import com.soywiz.klock.DateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarFirstRowDayValueCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeTest
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Correctly calculates when current month first day is on monday`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7)
        result.firstRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when current month first day is on sunday`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 8, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(26, 27, 28, 29, 30, 31, 1)
        result.firstRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when previous month was leap february`() {
        val monthUnderTest = DateTime.createAdjusted(2020, 3, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(24, 25, 26, 27, 28, 29, 1)
        result.firstRowDaysEqual(expectedDays)
    }

    private fun List<List<CalendarDay>>.firstRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 0, expectedDays = expectedDays)
}
