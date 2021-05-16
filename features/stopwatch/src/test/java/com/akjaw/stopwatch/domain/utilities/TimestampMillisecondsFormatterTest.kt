package com.akjaw.stopwatch.domain.utilities

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.stream.Stream

internal class TimestampMillisecondsFormatterTest {

    private lateinit var systemUnderTest: TimestampMillisecondsFormatter

    @BeforeEach
    fun setUp() {
        systemUnderTest = TimestampMillisecondsFormatter()
    }

    @ParameterizedTest
    @ArgumentsSource(TimestampArgumentsProvider::class)
    fun `Correctly formats the timestamp`(
        timestamp: Long,
        expectedString: String
    ) {
        val result = systemUnderTest.format(timestamp)

        expectThat(result).isEqualTo(expectedString)
    }

    private class TimestampArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                testCase(timestamp = 0, expectedString = "00:00:000"),
                testCase(timestamp = 500, expectedString = "00:00:500"),
                testCase(timestamp = 59999, expectedString = "00:59:999"),
                testCase(timestamp = 60000, expectedString = "01:00:000"),
                testCase(timestamp = 90000, expectedString = "01:30:000"),
                testCase(timestamp = 3599999, expectedString = "59:59:999"),
                testCase(timestamp = 3600000, expectedString = "01:00:00"),
                testCase(timestamp = 86399999, expectedString = "23:59:59"),
                testCase(timestamp = 86400000, expectedString = "24:00:00"),
                testCase(timestamp = 86401000, expectedString = "24:00:01"),
            )
        }

        private fun testCase(timestamp: Long, expectedString: String): Arguments =
            Arguments.of(timestamp, expectedString)
    }
}
