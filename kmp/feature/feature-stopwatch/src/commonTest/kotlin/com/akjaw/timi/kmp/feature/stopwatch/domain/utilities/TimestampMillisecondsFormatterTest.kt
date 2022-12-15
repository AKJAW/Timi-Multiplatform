package com.akjaw.timi.kmp.feature.stopwatch.domain.utilities

import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class TimestampMillisecondsFormatterTest {

    private lateinit var systemUnderTest: TimestampMillisecondsFormatter

    @BeforeTest
    fun setUp() {
        systemUnderTest = TimestampMillisecondsFormatter()
    }

    @Test
    fun `Timestamp 0 becomes "00 00 000"`() {
        formatTestCase(0, "00:00:000")
    }

    @Test
    fun `Timestamp 500 becomes "00 00 500"`() {
        formatTestCase(500, "00:00:500")
    }

    @Test
    fun `Timestamp 59999 becomes "00 59 999"`() {
        formatTestCase(59999, "00:59:999")
    }

    @Test
    fun `Timestamp 60000 becomes "01 00 000"`() {
        formatTestCase(60000, "01:00:000")
    }

    @Test
    fun `Timestamp 90000 becomes "01 30 000"`() {
        formatTestCase(90000, "01:30:000")
    }

    @Test
    fun `Timestamp 3599999 becomes "59 59 999"`() {
        formatTestCase(3599999, "59:59:999")
    }

    @Test
    fun `Timestamp 3600000 becomes "01 00 00"`() {
        formatTestCase(3600000, "01:00:00")
    }

    @Test
    fun `Timestamp 86399999 becomes "23 59 59"`() {
        formatTestCase(86399999, "23:59:59")
    }

    @Test
    fun `Timestamp 86400000 becomes "24 00 00"`() {
        formatTestCase(86400000, "24:00:00")
    }

    @Test
    fun `Timestamp 86401000 becomes "24 00 01"`() {
        formatTestCase(86401000, "24:00:01")
    }

    private fun formatTestCase(
        timestamp: Long,
        expectedString: String
    ) {
        val result = systemUnderTest.format(timestamp.toTimestampMilliseconds())

        result shouldBe expectedString
    }
}
