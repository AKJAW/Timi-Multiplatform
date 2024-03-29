package com.akjaw.timi.kmp.feature.stopwatch.domain.calculator

import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.core.test.time.MockTimestampProvider
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.model.StopwatchState
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class StopwatchRunningStateCalculationTest {

    private val mockTimestampProvider = MockTimestampProvider()
    private val elapsedTimeCalculator = ElapsedTimeCalculator(mockTimestampProvider)
    private lateinit var systemUnderTest: StopwatchStateCalculator

    @BeforeTest
    fun setUp() {
        systemUnderTest = StopwatchStateCalculator(
            timestampProvider = mockTimestampProvider,
            elapsedTimeCalculator = elapsedTimeCalculator
        )
    }

    @Test
    fun `Running state from Paused 0 is Running - start 0 and elapsed 0`() {
        calculateRunningTestCase(
            currentTimestamp = 0,
            oldState = StopwatchState.Paused(elapsedTime = 0.toTimestampMilliseconds()),
            expectedState = StopwatchState.Running(
                startTime = 0.toTimestampMilliseconds(),
                elapsedTime = 0.toTimestampMilliseconds()
            )
        )
    }

    @Test
    fun `Elapsed time from Paused is included in Running state`() {
        calculateRunningTestCase(
            currentTimestamp = 1000,
            oldState = StopwatchState.Paused(elapsedTime = 200.toTimestampMilliseconds()),
            expectedState = StopwatchState.Running(
                startTime = 1000.toTimestampMilliseconds(),
                elapsedTime = 200.toTimestampMilliseconds()
            )
        )
    }

    @Test
    fun `Running state does not change if no time passed`() {
        calculateRunningTestCase(
            currentTimestamp = 0,
            oldState = StopwatchState.Running(
                startTime = 1000.toTimestampMilliseconds(),
                elapsedTime = 200.toTimestampMilliseconds()
            ),
            expectedState = StopwatchState.Running(
                startTime = 1000.toTimestampMilliseconds(),
                elapsedTime = 200.toTimestampMilliseconds()
            )
        )
    }

    private fun calculateRunningTestCase(
        currentTimestamp: Long,
        oldState: StopwatchState,
        expectedState: StopwatchState.Running
    ) {
        mockTimestampProvider.givenTimePassed(currentTimestamp)

        val result = systemUnderTest.calculateRunningState(oldState)

        result shouldBe expectedState
    }
}
