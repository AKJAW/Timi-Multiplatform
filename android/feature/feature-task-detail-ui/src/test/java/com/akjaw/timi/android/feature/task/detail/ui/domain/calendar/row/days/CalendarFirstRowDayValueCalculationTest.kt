package com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.row.days

import com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.rowDaysEqual
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDay
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.Assertion
import strikt.api.expectThat
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarFirstRowDayValueCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Correctly calculates when current month first day is on monday`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7)
        expectThat(result).firstRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when current month first day is on sunday`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 8, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(26, 27, 28, 29, 30, 31, 1)
        expectThat(result).firstRowDaysEqual(expectedDays)
    }

    @Test
    fun `Correctly calculates when previous month was leap february`() {
        val monthUnderTest = DateTime.createAdjusted(2020, 3, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        val expectedDays = listOf(24, 25, 26, 27, 28, 29, 1)
        expectThat(result).firstRowDaysEqual(expectedDays)
    }

    private fun Assertion.Builder<List<List<CalendarDay>>>.firstRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 0, expectedDays = expectedDays)
}
