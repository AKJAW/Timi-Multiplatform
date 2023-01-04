package com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.row.days

import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.rowDaysEqual
import com.soywiz.klock.DateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarLastRowDayValueCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeTest
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Correctly calculates when current month is on the last row`() {
        val monthUnderTest = DateTime.createAdjusted(2022, 1, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(31, 1, 2, 3, 4, 5, 6)
        result.lastRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when next month starts the last row`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 10, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7)
        result.lastRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when next month starts in the middle of the second to last row`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 6, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(5, 6, 7, 8, 9, 10, 11)
        result.lastRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when next month fills last two rows`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(8, 9, 10, 11, 12, 13, 14)
        result.lastRowDaysEqual(expectedDays)
    }

    private fun List<List<CalendarDay>>.lastRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 5, expectedDays = expectedDays)
}
