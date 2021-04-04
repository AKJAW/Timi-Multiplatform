package com.example.timicompose.stopwatch.domain

import com.example.timicompose.common.domain.TimestampProvider
import com.example.timicompose.common.domain.model.toTimestampMilliseconds
import com.example.timicompose.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.example.timicompose.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class StopwatchStateHolderTest {

    private val timestampProvider: TimestampProvider = mockk()
    private val elapsedTimeCalculator = ElapsedTimeCalculator(timestampProvider)
    private val stopwatchStateCalculator = StopwatchStateCalculator(
        timestampProvider = timestampProvider,
        elapsedTimeCalculator = elapsedTimeCalculator
    )
    private val timestampMillisecondsFormatter = TimestampMillisecondsFormatter()
    private lateinit var systemUnderTest: StopwatchStateHolder

    @BeforeEach
    fun setUp() {
        systemUnderTest = StopwatchStateHolder(
            stopwatchStateCalculator = stopwatchStateCalculator,
            elapsedTimeCalculator = elapsedTimeCalculator,
            timestampMillisecondsFormatter = timestampMillisecondsFormatter
        )
    }

    @Nested
    inner class Started {

        @Test
        fun `String is correctly formatted after no time passes`() {
            givenNoTimePasses()
            systemUnderTest.start()

            val result = systemUnderTest.getStringTimeRepresentation()

            expectThat(result).isEqualTo("00:00:000")
        }

        @Test
        fun `String is correctly formatted after some time passes from start`() {
            givenTimePassesAfterStart(59999)
            systemUnderTest.start()

            val result = systemUnderTest.getStringTimeRepresentation()

            expectThat(result).isEqualTo("00:59:999")
        }
    }

    @Nested
    inner class Paused {

        @Test
        fun `String is correctly formatted after no time passes`() {
            givenNoTimePasses()
            systemUnderTest.start()
            systemUnderTest.pause()

            val result = systemUnderTest.getStringTimeRepresentation()

            expectThat(result).isEqualTo("00:00:000")
        }

        @Test
        fun `String is correctly formatted after some time passes from start`() {
            givenTimePassesAfterStart(59999)
            systemUnderTest.start()
            systemUnderTest.pause()

            val result = systemUnderTest.getStringTimeRepresentation()

            expectThat(result).isEqualTo("00:59:999")
        }
    }

    private fun givenNoTimePasses() {
        every { timestampProvider.getMilliseconds() } returns 0.toTimestampMilliseconds()
    }

    private fun givenTimePassesAfterStart(amount: Int) {
        every { timestampProvider.getMilliseconds() }
            .returnsMany(
                listOf(
                    0.toTimestampMilliseconds(),
                    amount.toTimestampMilliseconds(),
                )
            )
    }
}
