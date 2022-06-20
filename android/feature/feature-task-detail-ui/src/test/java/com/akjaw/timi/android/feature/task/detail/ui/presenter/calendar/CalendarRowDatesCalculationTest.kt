package com.akjaw.timi.android.feature.task.detail.ui.presenter.calendar

import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarRowDatesCalculationTest {

    private lateinit var systemUnderTest: com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest =
            com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator()
    }

    @Nested
    inner class FirstRow {

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

    @Nested
    inner class MiddleRows {

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

    @Nested
    inner class LastRow {

        @Test
        fun `Assigns correct dates for month where last row is only next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result[5]) {
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

            expectThat(result[5]) {
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

            expectThat(result[5]) {
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

    private fun Assertion.Builder<List<com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDay>>.rowWithOneMonthHasCorrectDates(
        month: Int,
        year: Int
    ) {
        get(0).hasCorrectDate(month, year)
        get(1).hasCorrectDate(month, year)
        get(2).hasCorrectDate(month, year)
        get(3).hasCorrectDate(month, year)
        get(4).hasCorrectDate(month, year)
        get(5).hasCorrectDate(month, year)
        get(6).hasCorrectDate(month, year)
    }

    private fun Assertion.Builder<List<com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDay>>.rowWithTwoMonthsHasCorrectDates(
        monthsAndYears: List<Pair<Int, Int>>
    ) {
        get(0).hasCorrectDate(monthsAndYears[0].first, monthsAndYears[0].second)
        get(1).hasCorrectDate(monthsAndYears[1].first, monthsAndYears[1].second)
        get(2).hasCorrectDate(monthsAndYears[2].first, monthsAndYears[2].second)
        get(3).hasCorrectDate(monthsAndYears[3].first, monthsAndYears[3].second)
        get(4).hasCorrectDate(monthsAndYears[4].first, monthsAndYears[4].second)
        get(5).hasCorrectDate(monthsAndYears[5].first, monthsAndYears[5].second)
        get(6).hasCorrectDate(monthsAndYears[6].first, monthsAndYears[6].second)
    }

    private fun Assertion.Builder<com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDay>.hasCorrectDate(
        month: Int,
        year: Int,
    ) {
        get { this.month }.isEqualTo(month)
        get { this.year }.isEqualTo(year)
    }
}
