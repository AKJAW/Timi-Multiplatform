package com.akjaw.timi.kmp.core.shared.time

import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds
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
    fun `Timestamp with milliseconds 0 becomes "00 00 000"`() {
        formatWithMillisecondsTestCase(0, "00:00:000")
    }

    @Test
    fun `Timestamp with milliseconds 500 becomes "00 00 500"`() {
        formatWithMillisecondsTestCase(500, "00:00:500")
    }

    @Test
    fun `Timestamp with milliseconds 59999 becomes "00 59 999"`() {
        formatWithMillisecondsTestCase(59999, "00:59:999")
    }

    @Test
    fun `Timestamp with milliseconds 60000 becomes "01 00 000"`() {
        formatWithMillisecondsTestCase(60000, "01:00:000")
    }

    @Test
    fun `Timestamp with milliseconds 90000 becomes "01 30 000"`() {
        formatWithMillisecondsTestCase(90000, "01:30:000")
    }

    @Test
    fun `Timestamp with milliseconds 3599999 becomes "59 59 999"`() {
        formatWithMillisecondsTestCase(3599999, "59:59:999")
    }

    @Test
    fun `Timestamp without milliseconds 0 becomes "00 00"`() {
        formatWithoutMillisecondsTestCase(0, "00:00")
    }

    @Test
    fun `Timestamp without milliseconds 499 becomes "00 00"`() {
        formatWithoutMillisecondsTestCase(499, "00:00")
    }

    @Test
    fun `Timestamp without milliseconds 500 becomes "00 01"`() {
        formatWithoutMillisecondsTestCase(500, "00:01")
    }

    @Test
    fun `Timestamp without milliseconds 59999 becomes "01 00"`() {
        formatWithoutMillisecondsTestCase(59999, "01:00")
    }

    @Test
    fun `Timestamp without milliseconds 60000 becomes "01 00"`() {
        formatWithoutMillisecondsTestCase(60000, "01:00")
    }

    @Test
    fun `Timestamp without milliseconds 90000 becomes "01 30"`() {
        formatWithoutMillisecondsTestCase(90000, "01:30")
    }

    @Test
    fun `Timestamp without milliseconds 3599999 becomes "01 00 00"`() {
        formatWithoutMillisecondsTestCase(3599999, "01:00:00")
    }

    @Test
    fun `Timestamp 3600000 becomes "01 00 00"`() {
        formatWithMillisecondsTestCase(3600000, "01:00:00")
    }

    @Test
    fun `Timestamp 86399999 becomes "23 59 59"`() {
        formatWithMillisecondsTestCase(86399999, "23:59:59")
    }

    @Test
    fun `Timestamp 86400000 becomes "24 00 00"`() {
        formatWithMillisecondsTestCase(86400000, "24:00:00")
    }

    @Test
    fun `Timestamp 86401000 becomes "24 00 01"`() {
        formatWithMillisecondsTestCase(86401000, "24:00:01")
    }

    private fun formatWithMillisecondsTestCase(
        timestamp: Long,
        expectedString: String
    ) {
        val result = systemUnderTest.formatWithMilliseconds(timestamp.toTimestampMilliseconds())

        result shouldBe expectedString
    }

    private fun formatWithoutMillisecondsTestCase(
        timestamp: Long,
        expectedString: String
    ) {
        val result = systemUnderTest.formatWithoutMilliseconds(timestamp.toTimestampMilliseconds())

        result shouldBe expectedString
    }
}
