package com.akjaw.stopwatch.domain

import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateHolder
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import com.akjaw.timi.kmp.feature.stopwatch.domain.model.StopwatchState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.DescribeableBuilder
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

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

    private val stopwatchStateHolder: StopwatchStateHolder = mockk(relaxed = true) {
        every { getStringTimeRepresentation() } returns ""
    }
    private val stopwatchStateHolderFactory: StopwatchStateHolderFactory = mockk {
        every { create() } returns stopwatchStateHolder
    }
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private lateinit var systemUnderTest: StopwatchListOrchestrator

    @BeforeEach
    fun setUp() {
        systemUnderTest = StopwatchListOrchestrator(
            stopwatchStateHolderFactory = stopwatchStateHolderFactory,
            scope = coroutineScope
        )
    }

    @Test
    fun `Initially the scope is inactive`() {
        coroutineDispatcher.advanceTimeBy(1000)

        expectThat(coroutineScope).hasChildrenCount(0)
    }

    @Test
    fun `When the first stopwatch is started then scope should become active`() {
        systemUnderTest.start(task = TASK1)

        coroutineDispatcher.advanceTimeBy(1000)
        expectThat(coroutineScope).hasChildrenCount(1)
    }

    @Test
    fun `When the last task is stopped then the scope should become inactive`() {
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        systemUnderTest.stop(task = TASK1)

        expectThat(coroutineScope).hasChildrenCount(0)
    }

    @Test
    fun `When a stopped stopwatch is started again then the scope should become active`() {
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)
        systemUnderTest.stop(task = TASK1)

        systemUnderTest.start(task = TASK1)

        expectThat(coroutineScope).hasChildrenCount(1)
    }

    @Test
    fun `When every stopwatch is paused then the scope should become inactive`() {
        every { stopwatchStateHolder.currentState }
            .returns(StopwatchState.Paused(0.toTimestampMilliseconds()))
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK2)
        systemUnderTest.pause(task = TASK2)

        systemUnderTest.pause(task = TASK1)

        expectThat(coroutineScope).hasChildrenCount(0)
    }

    @Test
    fun `When a paused stopwatch is started then the scope should become active`() {
        every { stopwatchStateHolder.currentState }
            .returns(StopwatchState.Paused(0.toTimestampMilliseconds()))
        systemUnderTest.start(task = TASK1)
        systemUnderTest.pause(task = TASK1)

        systemUnderTest.start(task = TASK1)

        expectThat(coroutineScope).hasChildrenCount(1)
    }

    private fun DescribeableBuilder<CoroutineScope>.hasChildrenCount(count: Int) {
        val job = coroutineScope.coroutineContext.job
        expectThat(job.children.count()).isEqualTo(count)
    }
}
