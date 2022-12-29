package com.akjaw.timi.kmp.feature.stopwatch.domain

import com.akjaw.timi.kmp.core.test.time.MockTimestampProvider
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class StopwatchStateHolderTest {

    private val mockTimestampProvider = MockTimestampProvider()
    private val elapsedTimeCalculator = ElapsedTimeCalculator(mockTimestampProvider)
    private val stopwatchStateCalculator = StopwatchStateCalculator(
        timestampProvider = mockTimestampProvider,
        elapsedTimeCalculator = elapsedTimeCalculator
    )
    private val timestampMillisecondsFormatter = TimestampMillisecondsFormatter()
    private lateinit var systemUnderTest: StopwatchStateHolder

    @BeforeTest
    fun setUp() {
        systemUnderTest = StopwatchStateHolder(
            stopwatchStateCalculator = stopwatchStateCalculator,
            elapsedTimeCalculator = elapsedTimeCalculator,
            timestampMillisecondsFormatter = timestampMillisecondsFormatter
        )
    }

    @Test
    fun `When Started String is correctly formatted after no time passes`() {
        mockTimestampProvider.givenTimePassed(0)
        systemUnderTest.start()

        val result = systemUnderTest.getStringTimeRepresentation()

        result shouldBe "00:00:000"
    }

    @Test
    fun `When Started String is correctly formatted after some time passes from start`() {
        mockTimestampProvider.givenTimePassed(0)
        systemUnderTest.start()
        mockTimestampProvider.givenTimePassed(59999)

        val result = systemUnderTest.getStringTimeRepresentation()

        result shouldBe "00:59:999"
    }

    @Test
    fun `When Paused String is correctly formatted after no time passes`() {
        mockTimestampProvider.givenTimePassed(0)
        systemUnderTest.start()
        systemUnderTest.pause()

        val result = systemUnderTest.getStringTimeRepresentation()

        result shouldBe "00:00:000"
    }

    @Test
    fun `When Paused String is correctly formatted after some time passes from start`() {
        mockTimestampProvider.givenTimePassed(0)
        systemUnderTest.start()
        mockTimestampProvider.givenTimePassed(59999)
        systemUnderTest.pause()

        val result = systemUnderTest.getStringTimeRepresentation()

        result shouldBe "00:59:999"
    }
}
