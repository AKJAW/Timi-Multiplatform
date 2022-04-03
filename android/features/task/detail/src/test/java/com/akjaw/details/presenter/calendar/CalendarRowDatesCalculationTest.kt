package com.akjaw.details.presenter.calendar

import com.akjaw.details.domain.calendar.CalendarDay
import com.akjaw.details.domain.calendar.CalendarDaysCalculator
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

    private data class RowDateAssertionPayload(
        val monthDaysCount: Int,
        val monthNumber: Int,
        val monthYear: Int,
        val nextMonthNumber: Int,
        val nextMonthYear: Int
    )

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
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 3,
                        monthNumber = 6,
                        monthYear = 2021,
                        nextMonthNumber = 7,
                        nextMonthYear = 2021
                    ),
                )
            }
        }

        @Test
        fun `Assigns correct dates for January`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 1, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                firstRowHasCorrectDate(
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 4,
                        monthNumber = 12,
                        monthYear = 2020,
                        nextMonthNumber = 1,
                        nextMonthYear = 2021
                    ),
                )
            }
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.firstRowHasCorrectDate(
            payload: RowDateAssertionPayload
        ) {
            rowWithTwoMonthsHasCorrectDate(
                rowIndex = 0,
                payload = payload
            )
        }
    }

    @Nested
    inner class MiddleRows {

        @Test
        fun `Assigns correct dates when the middle row ends on the current month`() {
            val monthUnderTest = DateTime.createAdjusted(2022, 5, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowHasCorrectDate(
                    monthNumber = 5,
                    monthYear = 2022,
                )
                thirdRowHasCorrectDate(
                    monthNumber = 5,
                    monthYear = 2022,
                )
                fourthRowHasCorrectDate(
                    monthNumber = 5,
                    monthYear = 2022,
                )
                fifthRowHasCorrectDate(
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 7,
                        monthNumber = 5,
                        monthYear = 2022,
                        nextMonthNumber = 0,
                        nextMonthYear = 0
                    )
                )
            }
        }

        @Test
        fun `Assigns correct dates when the middle row contains current and next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 11, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowHasCorrectDate(
                    monthNumber = 11,
                    monthYear = 2021,
                )
                thirdRowHasCorrectDate(
                    monthNumber = 11,
                    monthYear = 2021,
                )
                fourthRowHasCorrectDate(
                    monthNumber = 11,
                    monthYear = 2021,
                )
                fifthRowHasCorrectDate(
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 2,
                        monthNumber = 11,
                        monthYear = 2021,
                        nextMonthNumber = 12,
                        nextMonthYear = 2021
                    )
                )
            }
        }

        @Test
        fun `Assigns correct dates for december`() {
            val monthUnderTest = DateTime.createAdjusted(2020, 12, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowHasCorrectDate(
                    monthNumber = 12,
                    monthYear = 2020,
                )
                thirdRowHasCorrectDate(
                    monthNumber = 12,
                    monthYear = 2020,
                )
                fourthRowHasCorrectDate(
                    monthNumber = 12,
                    monthYear = 2020,
                )
                fifthRowHasCorrectDate(
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 4,
                        monthNumber = 12,
                        monthYear = 2020,
                        nextMonthNumber = 1,
                        nextMonthYear = 2021
                    )
                )
            }
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.secondRowHasCorrectDate(
            monthNumber: Int,
            monthYear: Int
        ) {
            rowWithOneMonthHasCorrectDate(
                rowIndex = 1,
                monthNumber = monthNumber,
                monthYear = monthYear
            )
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.thirdRowHasCorrectDate(
            monthNumber: Int,
            monthYear: Int
        ) {
            rowWithOneMonthHasCorrectDate(
                rowIndex = 2,
                monthNumber = monthNumber,
                monthYear = monthYear
            )
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.fourthRowHasCorrectDate(
            monthNumber: Int,
            monthYear: Int
        ) {
            rowWithOneMonthHasCorrectDate(
                rowIndex = 3,
                monthNumber = monthNumber,
                monthYear = monthYear
            )
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.fifthRowHasCorrectDate(
            payload: RowDateAssertionPayload
        ) {
            rowWithTwoMonthsHasCorrectDate(
                rowIndex = 4,
                payload = payload
            )
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.rowWithOneMonthHasCorrectDate(
            rowIndex: Int,
            monthNumber: Int,
            monthYear: Int
        ) {
            rowWithTwoMonthsHasCorrectDate(
                rowIndex = rowIndex,
                payload = RowDateAssertionPayload(
                    monthDaysCount = 7,
                    monthNumber = monthNumber,
                    monthYear = monthYear,
                    nextMonthNumber = 0,
                    nextMonthYear = 0
                )
            )
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
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 0,
                        monthNumber = 7,
                        monthYear = 2021,
                        nextMonthNumber = 8,
                        nextMonthYear = 2021
                    ),
                )
            }
        }

        @Test
        fun `Assigns correct dates for month where last row contains current and next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 5, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                lastRowHasCorrectDate(
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 1,
                        monthNumber = 5,
                        monthYear = 2021,
                        nextMonthNumber = 6,
                        nextMonthYear = 2021
                    ),
                )
            }
        }

        @Test
        fun `Assigns correct dates for December`() {
            val monthUnderTest = DateTime.createAdjusted(2019, 12, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                lastRowHasCorrectDate(
                    payload = RowDateAssertionPayload(
                        monthDaysCount = 2,
                        monthNumber = 12,
                        monthYear = 2019,
                        nextMonthNumber = 1,
                        nextMonthYear = 2020
                    ),
                )
            }
        }

        private fun Assertion.Builder<List<List<CalendarDay>>>.lastRowHasCorrectDate(
            payload: RowDateAssertionPayload
        ) {
            rowWithTwoMonthsHasCorrectDate(
                rowIndex = 5,
                payload = payload
            )
        }
    }

    private fun Assertion.Builder<List<List<CalendarDay>>>.rowWithTwoMonthsHasCorrectDate(
        rowIndex: Int,
        payload: RowDateAssertionPayload
    ) {
        val row = this[rowIndex]
        (0 until payload.monthDaysCount).forEach { previousMonthIndex ->
            row[previousMonthIndex].and {
                get { month }.isEqualTo(payload.monthNumber)
                get { year }.isEqualTo(payload.monthYear)
            }
        }
        (payload.monthDaysCount + 1 until 7).forEach { currentMonthIndex ->
            row[currentMonthIndex].and {
                get { month }.isEqualTo(payload.nextMonthNumber)
                get { year }.isEqualTo(payload.nextMonthYear)
            }
        }
    }
}
