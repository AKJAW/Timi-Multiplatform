package com.akjaw.timi.android.feature.task.detail.ui.presenter.calendar.row.dates

import com.akjaw.timi.android.feature.task.detail.ui.presenter.calendar.rowWithTwoMonthsHasCorrectDates
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarRowDatesCalculationFirstRowTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Assigns correct dates for month in the middle of the year`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result[0]) {
            rowWithTwoMonthsHasCorrectDates(
                listOf(
                    6 to 2021,
                    6 to 2021,
                    6 to 2021,
                    7 to 2021,
                    7 to 2021,
                    7 to 2021,
                    7 to 2021,
                )
            )
        }
    }

    @Test
    fun `Assigns correct dates for January`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 1, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result[0]) {
            rowWithTwoMonthsHasCorrectDates(
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
