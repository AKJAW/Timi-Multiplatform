package com.akjaw.timi.kmp.feature.stopwatch.domain.calculator

import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.core.test.time.MockTimestampProvider
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.model.StopwatchState
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class StopwatchPausedStateCalculationTest {

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
    fun `Elapsed time is equal to time passed in Running state`() {
        calculatePausedStateTestCase(
            currentTimestamp = 1500,
            oldState = StopwatchState.Running(
                startTime = 1000.toTimestampMilliseconds(),
                elapsedTime = 0.toTimestampMilliseconds()
            ),
            expectedState = StopwatchState.Paused(
                elapsedTime = (1500 - 1000).toTimestampMilliseconds()
            )
        )
    }

    @Test
    fun `Previous elapsed time is added together when time passes in Running state`() {
        calculatePausedStateTestCase(
            currentTimestamp = 1500,
            oldState = StopwatchState.Running(
                startTime = 1000.toTimestampMilliseconds(),
                elapsedTime = 300.toTimestampMilliseconds()
            ),
            expectedState = StopwatchState.Paused(
                elapsedTime = (1500 - 1000 + 300).toTimestampMilliseconds()
            )
        )
    }

    @Test
    fun `Elapsed time does not change if Running start was in the future`() {
        calculatePausedStateTestCase(
            currentTimestamp = 500,
            oldState = StopwatchState.Running(
                startTime = 1000.toTimestampMilliseconds(),
                elapsedTime = 300.toTimestampMilliseconds()
            ),
            expectedState = StopwatchState.Paused(
                elapsedTime = 300.toTimestampMilliseconds()
            )
        )
    }

    @Test
    fun `Elapsed time does not change when previous state was Paused`() {
        calculatePausedStateTestCase(
            currentTimestamp = 500,
            oldState = StopwatchState.Paused(
                elapsedTime = 200.toTimestampMilliseconds()
            ),
            expectedState = StopwatchState.Paused(
                elapsedTime = 200.toTimestampMilliseconds()
            )
        )
    }

    private fun calculatePausedStateTestCase(
        currentTimestamp: Long,
        oldState: StopwatchState,
        expectedState: StopwatchState.Paused
    ) {
        mockTimestampProvider.givenTimePassed(currentTimestamp)

        val result = systemUnderTest.calculatePausedState(oldState)

        result shouldBe expectedState
    }
}
