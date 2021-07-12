package com.akjaw.details.presenter.calendar

import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CalendarRowDatesTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Nested
    inner class FirstRow {

        @Test
        fun `Assigns correct dates for month in the middle of the year`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                firstRowHasCorrectDate(
                    previousMonthDaysCount = 3,
                    previousMonthNumber = 6,
                    previousMonthYear = 2021,
                    currentMonthNumber = 7,
                    currentMonthYear = 2021,
                )
            }
        }

        @Test
        fun `Assigns correct dates for January`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 1, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                firstRowHasCorrectDate(
                    previousMonthDaysCount = 4,
                    previousMonthNumber = 12,
                    previousMonthYear = 2020,
                    currentMonthNumber = 1,
                    currentMonthYear = 2021,
                )
            }
        }

        private fun Assertion.Builder<List<List<Day>>>.firstRowHasCorrectDate(
            previousMonthDaysCount: Int,
            previousMonthNumber: Int,
            previousMonthYear: Int,
            currentMonthNumber: Int,
            currentMonthYear: Int
        ) {
            val firstRow = this[0]
            (0 until previousMonthDaysCount).forEach { previousMonthIndex ->
                firstRow[previousMonthIndex].and {
                    get { month }.isEqualTo(previousMonthNumber)
                    get { year }.isEqualTo(previousMonthYear)
                }
            }
            (previousMonthDaysCount + 1 until 7).forEach { currentMonthIndex ->
                firstRow[currentMonthIndex].and {
                    get { month }.isEqualTo(currentMonthNumber)
                    get { year }.isEqualTo(currentMonthYear)
                }
            }
        }
    }

    @Nested
    inner class LastRow {

        @Test
        fun `Assigns correct dates for month where last row is only next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                lastRowHasCorrectDate(
                    currentMonthDaysCount = 0,
                    currentMonthNumber = 7,
                    currentMonthYear = 2021,
                    nextMonthNumber = 8,
                    nextMonthYear = 2021,
                )
            }
        }

        @Test
        fun `Assigns correct dates for month where last row contains current and next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 5, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                lastRowHasCorrectDate(
                    currentMonthDaysCount = 1,
                    currentMonthNumber = 5,
                    currentMonthYear = 2021,
                    nextMonthNumber = 6,
                    nextMonthYear = 2021,
                )
            }
        }

        @Test
        fun `Assigns correct dates for December`() {
            val monthUnderTest = DateTime.createAdjusted(2019, 12, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                lastRowHasCorrectDate(
                    currentMonthDaysCount = 2,
                    currentMonthNumber = 12,
                    currentMonthYear = 2019,
                    nextMonthNumber = 1,
                    nextMonthYear = 2020,
                )
            }
        }
    }

    private fun Assertion.Builder<List<List<Day>>>.lastRowHasCorrectDate(
        currentMonthDaysCount: Int,
        currentMonthNumber: Int,
        currentMonthYear: Int,
        nextMonthNumber: Int,
        nextMonthYear: Int
    ) {
        val firstRow = this[5]
        (0 until currentMonthDaysCount).forEach { previousMonthIndex ->
            firstRow[previousMonthIndex].and {
                get { month }.isEqualTo(currentMonthNumber)
                get { year }.isEqualTo(currentMonthYear)
            }
        }
        (currentMonthDaysCount + 1 until 7).forEach { currentMonthIndex ->
            firstRow[currentMonthIndex].and {
                get { month }.isEqualTo(nextMonthNumber)
                get { year }.isEqualTo(nextMonthYear)
            }
        }
    }
}
