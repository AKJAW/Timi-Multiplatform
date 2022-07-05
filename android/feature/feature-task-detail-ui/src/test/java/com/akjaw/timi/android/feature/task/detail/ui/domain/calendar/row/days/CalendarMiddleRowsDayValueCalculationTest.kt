package com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.row.days

import com.akjaw.timi.android.feature.task.detail.ui.rowDaysEqual
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDay
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.soywiz.klock.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.Assertion
import strikt.api.expectThat
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarMiddleRowsDayValueCalculationTest {

    private lateinit var systemUnderTest: CalendarDaysCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = CalendarDaysCalculator()
    }

    @Test
    fun `Correctly calculates when the last middle row contains current and next month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 11, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            secondRowDaysEqual((8..14).toList())
            thirdRowDaysEqual((15..21).toList())
            fourthRowDaysEqual((22..28).toList())
            fifthRowDaysEqual(listOf(29, 30, 1, 2, 3, 4, 5))
        }
    }

    @Test
    fun `Correctly calculates when the middle row ends exactly one day before end of month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 5, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            secondRowDaysEqual((3..9).toList())
            thirdRowDaysEqual((10..16).toList())
            fourthRowDaysEqual((17..23).toList())
            fifthRowDaysEqual((24..30).toList())
        }
    }

    @Test
    fun `Correctly calculates when the middle row ends on the last day of the current month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 10, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            secondRowDaysEqual((4..10).toList())
            thirdRowDaysEqual((11..17).toList())
            fourthRowDaysEqual((18..24).toList())
            fifthRowDaysEqual((25..31).toList())
        }
    }

    @Test
    fun `Correctly calculates when the middle row ends on the first week of the next month`() {
        val monthUnderTest = DateTime.createAdjusted(2021, 2, 1)

        val result = systemUnderTest.calculate(monthUnderTest)

        expectThat(result) {
            secondRowDaysEqual((8..14).toList())
            thirdRowDaysEqual((15..21).toList())
            fourthRowDaysEqual((22..28).toList())
            fifthRowDaysEqual((1..7).toList())
        }
    }

    private fun Assertion.Builder<List<List<CalendarDay>>>.secondRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 1, expectedDays = expectedDays)

    private fun Assertion.Builder<List<List<CalendarDay>>>.thirdRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 2, expectedDays = expectedDays)

    private fun Assertion.Builder<List<List<CalendarDay>>>.fourthRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 3, expectedDays = expectedDays)

    private fun Assertion.Builder<List<List<CalendarDay>>>.fifthRowDaysEqual(
        expectedDays: List<Int>
    ) =
        rowDaysEqual(rowIndex = 4, expectedDays = expectedDays)
}
