package com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.row.dates

import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.rowWithTwoMonthsHasCorrectDates
import com.soywiz.klock.DateTime
import io.kotest.assertions.assertSoftly
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarFirstRowDatesCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeTest
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Assigns correct dates for month in the middle of the year`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        assertSoftly(result[0]) {
            rowWithTwoMonthsHasCorrectDates(
                listOf(
                    6 to 2021,
                    6 to 2021,
                    6 to 2021,
                    7 to 2021,
                    7 to 2021,
                    7 to 2021,
                    7 to 2021
                )
            )
        }
    }

    @Test
    fun `Assigns correct dates for January`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 1, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        assertSoftly(result[0]) {
            rowWithTwoMonthsHasCorrectDates(
                listOf(
                    12 to 2020,
                    12 to 2020,
                    12 to 2020,
                    12 to 2020,
                    1 to 2021,
                    1 to 2021,
                    1 to 2021
                )
            )
        }
    }
}
