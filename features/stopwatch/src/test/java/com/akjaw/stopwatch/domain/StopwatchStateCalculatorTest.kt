package com.akjaw.stopwatch.domain

import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.stopwatch.domain.model.StopwatchState
import com.akjaw.stopwatch.domain.utilities.ElapsedTimeCalculator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
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
            timestampProvider,
            elapsedTimeCalculator
        )
    }

    @ParameterizedTest
    @ArgumentsSource(ToRunningStateArgumentsProvider::class)
    fun `Correctly calculates running state`(
        startingTimestamp: TimestampMilliseconds,
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
                    startingTimestamp = 0.toTimestampMilliseconds(),
                    oldState = StopwatchState.Paused(elapsedTime = 0.toTimestampMilliseconds()),
                    expectedState = StopwatchState.Running(
                        startTime = 0.toTimestampMilliseconds(),
                        elapsedTime = 0.toTimestampMilliseconds(),
                    )
                ),
                testCase(
                    startingTimestamp = 1000.toTimestampMilliseconds(),
                    oldState = StopwatchState.Paused(elapsedTime = 200.toTimestampMilliseconds()),
                    expectedState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 200.toTimestampMilliseconds(),
                    )
                ),
                testCase(
                    startingTimestamp = 0.toTimestampMilliseconds(),
                    oldState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 200.toTimestampMilliseconds(),
                    ),
                    expectedState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 200.toTimestampMilliseconds(),
                    )
                )
            )
        }

        private fun testCase(
            startingTimestamp: TimestampMilliseconds,
            oldState: StopwatchState,
            expectedState: StopwatchState.Running,
        ) = Arguments.of(startingTimestamp, oldState, expectedState)
    }

    @ParameterizedTest
    @ArgumentsSource(ToPausedStateArgumentsProvider::class)
    fun `Correctly calculates paused state`(
        currentTimestamp: TimestampMilliseconds,
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
                    currentTimestamp = 1500.toTimestampMilliseconds(),
                    oldState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 0.toTimestampMilliseconds(),
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = (1500 - 1000).toTimestampMilliseconds()
                    )
                ),
                testCase(
                    currentTimestamp = 1500.toTimestampMilliseconds(),
                    oldState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 300.toTimestampMilliseconds(),
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = (1500 - 1000 + 300).toTimestampMilliseconds()
                    )
                ),
                testCase(
                    currentTimestamp = 500.toTimestampMilliseconds(),
                    oldState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 300.toTimestampMilliseconds(),
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = 300.toTimestampMilliseconds()
                    )
                ),
                testCase(
                    currentTimestamp = 500.toTimestampMilliseconds(),
                    oldState = StopwatchState.Running(
                        startTime = 1000.toTimestampMilliseconds(),
                        elapsedTime = 0.toTimestampMilliseconds(),
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = 0.toTimestampMilliseconds()
                    )
                ),
                testCase(
                    currentTimestamp = 500.toTimestampMilliseconds(),
                    oldState = StopwatchState.Paused(
                        elapsedTime = 200.toTimestampMilliseconds(),
                    ),
                    expectedState = StopwatchState.Paused(
                        elapsedTime = 200.toTimestampMilliseconds(),
                    ),
                )
            )
        }

        private fun testCase(
            currentTimestamp: TimestampMilliseconds,
            oldState: StopwatchState,
            expectedState: StopwatchState.Paused,
        ) = Arguments.of(currentTimestamp, oldState, expectedState)
    }
}
