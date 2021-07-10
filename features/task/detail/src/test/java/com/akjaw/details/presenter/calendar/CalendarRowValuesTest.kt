package com.akjaw.details.presenter.calendar

import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.isEqualTo
import strikt.assertions.map
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CalendarRowValuesTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Nested
    inner class FirstRow {

        @Test
        fun `Correctly calculates when current month first day is on tuesday`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 7, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(28, 29, 30, 1, 2, 3, 4)
            expectThat(result).firstRowEquals(expectedDays)
        }

        @Test
        fun `Correctly calculates when current month first day is on monday`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7)
            expectThat(result).firstRowEquals(expectedDays)
        }

        @Test
        fun `Correctly calculates when current month first day is on sunday`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 8, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(26, 27, 28, 29, 30, 31, 1)
            expectThat(result).firstRowEquals(expectedDays)
        }

        @Test
        fun `Correctly calculates when previous month was leap february`() {
            val monthUnderTest = DateTime.createAdjusted(2020, 3, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(24, 25, 26, 27, 28, 29, 1)
            expectThat(result).firstRowEquals(expectedDays)
        }

        private fun Assertion.Builder<List<List<Day>>>.firstRowEquals(expectedDays: List<Int>) =
            rowEquals(0, expectedDays)
    }

    @Nested
    inner class LastRow {

        @Test
        fun `Correctly calculates when current month is on the last row`() {
            val monthUnderTest = DateTime.createAdjusted(2022, 1, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(31, 1, 2, 3, 4, 5, 6)
            expectThat(result).lastRowEquals(expectedDays)
        }

        @Test
        fun `Correctly calculates when next month starts the last row`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 10, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(1, 2, 3, 4, 5, 6, 7)
            expectThat(result).lastRowEquals(expectedDays)
        }

        @Test
        fun `Correctly calculates when next month starts in the middle of the second to last row`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 6, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(5, 6, 7, 8, 9, 10, 11)
            expectThat(result).lastRowEquals(expectedDays)
        }

        @Test
        fun `Correctly calculates when next month fills last two rows`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            val expectedDays = listOf(8, 9, 10, 11, 12, 13, 14)
            expectThat(result).lastRowEquals(expectedDays)
        }

        private fun Assertion.Builder<List<List<Day>>>.lastRowEquals(expectedDays: List<Int>) =
            rowEquals(5, expectedDays)
    }

    @Nested
    inner class MiddleRows {

        @Test
        fun `Correctly calculates when the last middle row contains current and next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 11, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowEquals((8..14).toList())
                thirdRowEquals((15..21).toList())
                fourthRowEquals((22..28).toList())
                fifthRowEquals(listOf(29, 30, 1, 2, 3, 4, 5))
            }
        }

        @Test
        fun `Correctly calculates when the middle row ends before current month ends`() {
            val monthUnderTest = DateTime.createAdjusted(2022, 5, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowEquals((2..8).toList())
                thirdRowEquals((9..15).toList())
                fourthRowEquals((16..22).toList())
                fifthRowEquals((23..29).toList())
            }
        }

        @Test
        fun `Correctly calculates when the middle row ends on the last day of the current month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 10, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowEquals((4..10).toList())
                thirdRowEquals((11..17).toList())
                fourthRowEquals((18..24).toList())
                fifthRowEquals((25..31).toList())
            }
        }

        @Test
        fun `Correctly calculates when the middle row ends on the first week of the next month`() {
            val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

            val result = systemUnderTest.calculate(monthUnderTest)

            expectThat(result) {
                secondRowEquals((8..14).toList())
                thirdRowEquals((15..21).toList())
                fourthRowEquals((22..28).toList())
                fifthRowEquals((1..7).toList())
            }
        }

        private fun Assertion.Builder<List<List<Day>>>.secondRowEquals(expectedDays: List<Int>) =
            rowEquals(1, expectedDays)

        private fun Assertion.Builder<List<List<Day>>>.thirdRowEquals(expectedDays: List<Int>) =
            rowEquals(2, expectedDays)

        private fun Assertion.Builder<List<List<Day>>>.fourthRowEquals(expectedDays: List<Int>) =
            rowEquals(3, expectedDays)

        private fun Assertion.Builder<List<List<Day>>>.fifthRowEquals(expectedDays: List<Int>) =
            rowEquals(4, expectedDays)
    }

    private fun Assertion.Builder<List<List<Day>>>.rowEquals(
        rowIndex: Int,
        expectedDays: List<Int>,
    ) {
        val rowDays = this[rowIndex].map { it.value.toInt() }
        rowDays.isEqualTo(expectedDays)
    }
}
