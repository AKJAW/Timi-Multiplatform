package com.akjaw.timi.kmp.feature.stopwatch.domain.orchestrator

import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.timi.kmp.feature.stopwatch.domain.helpers.FakeTimestampProvider
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.feature.task.api.model.Task
import io.kotest.matchers.sequences.shouldHaveSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class StopwatchListOrchestratorCoroutineTest {

    companion object {
        private val TASK1 = Task(
            id = 1,
            name = "First task",
            isSelected = false
        )
        private val TASK2 = Task(
            id = 2,
            name = "Second the cooler task",
            isSelected = false
        )
    }

    private val fakeTimestampProvider = FakeTimestampProvider()
    private val elapsedTimeCalculator = ElapsedTimeCalculator(fakeTimestampProvider)
    private val stopwatchStateHolderFactory = StopwatchStateHolderFactory(
        stopwatchStateCalculator = StopwatchStateCalculator(
            fakeTimestampProvider,
            elapsedTimeCalculator
        ),
        elapsedTimeCalculator = elapsedTimeCalculator,
        timestampMillisecondsFormatter = TimestampMillisecondsFormatter(),
    )
    private val coroutineDispatcher = StandardTestDispatcher()
    private val coroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private lateinit var systemUnderTest: StopwatchListOrchestrator

    @BeforeTest
    fun setUp() {
        systemUnderTest = StopwatchListOrchestrator(
            stopwatchStateHolderFactory = stopwatchStateHolderFactory,
            scope = coroutineScope
        )
    }

    @Test
    fun `Initially the scope is inactive`() {
        advanceTime(1000)

        coroutineScope shouldHaveChildrenCount 0
    }

    @Test
    fun `When the first stopwatch is started then scope should become active`() {
        systemUnderTest.start(task = TASK1)

        advanceTime(1000)
        coroutineScope shouldHaveChildrenCount 1
    }

    @Test
    fun `When the last task is stopped then the scope should become inactive`() {
        systemUnderTest.start(task = TASK1)
        advanceTime(1000)

        systemUnderTest.stop(task = TASK1)

        coroutineScope shouldHaveChildrenCount 0
    }

    @Test
    fun `When a stopped stopwatch is started again then the scope should become active`() {
        systemUnderTest.start(task = TASK1)
        advanceTime(1000)
        systemUnderTest.stop(task = TASK1)

        systemUnderTest.start(task = TASK1)

        coroutineScope shouldHaveChildrenCount 1
    }

    @Test
    fun `When every stopwatch is paused then the scope should become inactive`() {
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK2)
        systemUnderTest.pause(task = TASK2)

        systemUnderTest.pause(task = TASK1)

        coroutineScope shouldHaveChildrenCount 0
    }

    @Test
    fun `When a paused stopwatch is started then the scope should become active`() {
        systemUnderTest.start(task = TASK1)
        systemUnderTest.pause(task = TASK1)

        systemUnderTest.start(task = TASK1)

        coroutineScope shouldHaveChildrenCount 1
    }

    private infix fun CoroutineScope.shouldHaveChildrenCount(count: Int) {
        advanceTime(1)
        val job = coroutineScope.coroutineContext.job
        job.children shouldHaveSize count
    }

    private fun advanceTime(amount: Int) {
        coroutineDispatcher.scheduler.advanceTimeBy(amount.toLong())
    }
}
