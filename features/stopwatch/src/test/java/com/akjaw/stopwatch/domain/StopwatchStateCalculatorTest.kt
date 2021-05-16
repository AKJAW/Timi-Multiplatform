package com.akjaw.stopwatch.domain

import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.stopwatch.domain.model.StopwatchState
import com.akjaw.stopwatch.domain.utilities.ElapsedTimeCalculator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.stream.Stream

internal class StopwatchStateCalculatorTest {

    private val timestampProvider: TimestampProvider = mockk()
    private val elapsedTimeCalculator = ElapsedTimeCalculator(timestampProvider)
    private lateinit var systemUnderTest: StopwatchStateCalculator

    @BeforeEach
    fun setUp() {
        systemUnderTest = StopwatchStateCalculator(
            timestampProvider = timestampProvider,
            elapsedTimeCalculator = elapsedTimeCalculator
        )
    }

    @ParameterizedTest
    @ArgumentsSource(ToRunningStateArgumentsProvider::class)
    fun `Correctly calculates running state`(
        startingTimestamp: Long,
        oldState: StopwatchState,
        expectedState: StopwatchState.Running,
    ) {
        every { timestampProvider.getMilliseconds() } returns startingTimestamp

        val result = systemUnderTest.calculateRunningState(oldState)

        expectThat(result).isEqualTo(expectedState)
    }

    class ToRunningStateArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                testCase(
                    startingTimestamp = 0,
                    oldState = StopwatchState.Paused(elapsedTime = 0),
                    expectedState = StopwatchState.Running(
                        startTime = 0,
                        elapsedTime = 0,
                    )
                ),
                testCase(
                    startingTimestamp = 1000,
                    oldState = StopwatchState.Paused(elapsedTime = 200),
                    expectedState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 200,
                    )
                ),
                testCase(
                    startingTimestamp = 0,
                    oldState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 200,
                    ),
                    expectedState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 200,
                    )
                )
            )
        }

        private fun testCase(
            startingTimestamp: Long,
            oldState: StopwatchState,
            expectedState: StopwatchState.Running,
        ) = Arguments.of(startingTimestamp, oldState, expectedState)
    }

    @ParameterizedTest
    @ArgumentsSource(ToPausedStateArgumentsProvider::class)
    fun `Correctly calculates paused state`(
        currentTimestamp: Long,
        oldState: StopwatchState,
        expectedState: StopwatchState.Paused,
    ) {
        every { timestampProvider.getMilliseconds() } returns currentTimestamp

        val result = systemUnderTest.calculatePausedState(oldState)

        expectThat(result).isEqualTo(expectedState)
    }

    class ToPausedStateArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                testCase(
                    currentTimestamp = 1500,
                    oldState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 0,
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = (1500 - 1000)
                    )
                ),
                testCase(
                    currentTimestamp = 1500,
                    oldState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 300,
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = (1500 - 1000 + 300)
                    )
                ),
                testCase(
                    currentTimestamp = 500,
                    oldState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 300,
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = 300
                    )
                ),
                testCase(
                    currentTimestamp = 500,
                    oldState = StopwatchState.Running(
                        startTime = 1000,
                        elapsedTime = 0,
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = 0
                    )
                ),
                testCase(
                    currentTimestamp = 500,
                    oldState = StopwatchState.Paused(
                        elapsedTime = 200,
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = 200,
                    ),
                )
            )
        }

        private fun testCase(
            currentTimestamp: Long,
            oldState: StopwatchState,
            expectedState: StopwatchState.Paused,
        ) = Arguments.of(currentTimestamp, oldState, expectedState)
    }

    @Test
    fun `Article example test`() {
        val timestampProvider: TimestampProvider = mockk()
        val elapsedTimeCalculator = ElapsedTimeCalculator(timestampProvider)
        val stopwatchStateCalculator = StopwatchStateCalculator(
            timestampProvider = timestampProvider,
            elapsedTimeCalculator = elapsedTimeCalculator
        )

        every { timestampProvider.getMilliseconds() } returns 0
        val initialState = StopwatchState.Paused(0L)
        val firstStart = stopwatchStateCalculator.calculateRunningState(initialState)
        expectThat(firstStart.startTime).isEqualTo(0L)
        expectThat(firstStart.elapsedTime).isEqualTo(0L)

        every { timestampProvider.getMilliseconds() } returns 100
        val firstPause = stopwatchStateCalculator.calculatePausedState(firstStart)
        expectThat(firstPause.elapsedTime).isEqualTo(100L)

        every { timestampProvider.getMilliseconds() } returns 1000
        val secondStart = stopwatchStateCalculator.calculateRunningState(firstPause)
        expectThat(secondStart.startTime).isEqualTo(1000L)
        expectThat(secondStart.elapsedTime).isEqualTo(100L)

        every { timestampProvider.getMilliseconds() } returns 1500
        val secondPause = stopwatchStateCalculator.calculatePausedState(secondStart)
        expectThat(secondPause.elapsedTime).isEqualTo(600L)
    }
}
