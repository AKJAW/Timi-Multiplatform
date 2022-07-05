package com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.row.dates

import com.akjaw.timi.android.feature.task.detail.ui.rowWithOneMonthHasCorrectDates
import com.akjaw.timi.android.feature.task.detail.ui.rowWithTwoMonthsHasCorrectDates
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.get
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarMiddleRowsDatesCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Assigns correct dates when the middle row ends on the current month`() {
        val monthUnderTest = DateTime.createAdjusted(2022, 5, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            this[1].rowWithOneMonthHasCorrectDates(month = 5, year = 2022)
            this[2].rowWithOneMonthHasCorrectDates(month = 5, year = 2022)
            this[3].rowWithOneMonthHasCorrectDates(month = 5, year = 2022)
            this[4].rowWithTwoMonthsHasCorrectDates(
                listOf(
                    5 to 2022,
                    5 to 2022,
                    5 to 2022,
                    5 to 2022,
                    5 to 2022,
                    5 to 2022,
                    5 to 2022,
                )
            )
        }
    }

    @Test
    fun `Assigns correct dates when the middle row contains current and next month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 11, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            this[1].rowWithOneMonthHasCorrectDates(month = 11, year = 2021)
            this[2].rowWithOneMonthHasCorrectDates(month = 11, year = 2021)
            this[3].rowWithOneMonthHasCorrectDates(month = 11, year = 2021)
            this[4].rowWithTwoMonthsHasCorrectDates(
                listOf(
                    11 to 2021,
                    11 to 2021,
                    12 to 2021,
                    12 to 2021,
                    12 to 2021,
                    12 to 2021,
                    12 to 2021,
                )
            )
        }
    }

    @Test
    fun `Assigns correct dates for december`() {
        val monthUnderTest = DateTime.createAdjusted(2020, 12, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            this[1].rowWithOneMonthHasCorrectDates(month = 12, year = 2020)
            this[2].rowWithOneMonthHasCorrectDates(month = 12, year = 2020)
            this[3].rowWithOneMonthHasCorrectDates(month = 12, year = 2020)
            this[4].rowWithTwoMonthsHasCorrectDates(
                listOf(
                    12 to 2020,
                    12 to 2020,
                    12 to 2020,
                    12 to 2020,
                    1 to 2021,
                    1 to 2021,
                    1 to 2021,
                )
            )
        }
    }
}
