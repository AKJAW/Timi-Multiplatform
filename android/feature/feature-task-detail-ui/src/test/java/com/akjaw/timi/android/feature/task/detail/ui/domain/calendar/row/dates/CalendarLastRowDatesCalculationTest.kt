package com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.row.dates

import com.akjaw.timi.android.feature.task.detail.ui.rowWithTwoMonthsHasCorrectDates
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.soywiz.klock.DateTime
import io.kotest.assertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarLastRowDatesCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Assigns correct dates for month where last row is only next month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        assertSoftly(result[5]) {
            rowWithTwoMonthsHasCorrectDates(
                listOf(
                    8 to 2021,
                    8 to 2021,
                    8 to 2021,
                    8 to 2021,
                    8 to 2021,
                    8 to 2021,
                    8 to 2021,
                )
            )
        }
    }

    @Test
    fun `Assigns correct dates for month where last row contains current and next month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 5, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        assertSoftly(result[5]) {
            rowWithTwoMonthsHasCorrectDates(
                listOf(
                    5 to 2021,
                    6 to 2021,
                    6 to 2021,
                    6 to 2021,
                    6 to 2021,
                    6 to 2021,
                    6 to 2021,
                )
            )
        }
    }

    @Test
    fun `Assigns correct dates for December`() {
        val monthUnderTest = DateTime.createAdjusted(2019, 12, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        assertSoftly(result[5]) {
            rowWithTwoMonthsHasCorrectDates(
                listOf(
                    12 to 2019,
                    12 to 2019,
                    1 to 2020,
                    1 to 2020,
                    1 to 2020,
                    1 to 2020,
                    1 to 2020,
                )
            )
        }
    }
}
