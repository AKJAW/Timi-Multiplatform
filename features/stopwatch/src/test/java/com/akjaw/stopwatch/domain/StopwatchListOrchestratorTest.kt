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

internal class StopwatchListOrchestratorTest {

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
    fun `Initially the stopwatch list is empty`() = runBlockingTest {
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEmpty()
    }

    @Test
    fun `When a stopwatch is started then it should be present in the list`() = runBlockingTest {
        givenStateHolderReturnsTime("0")
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expect {
            that(result).hasSize(1)
            val timeString = result[TASK1]
            that(timeString).isEqualTo("0")
        }
    }

    @Test
    fun `When a stopwatch is started multiple times then it should not be duplicated`() = runBlockingTest {
        givenStateHolderReturnsTime("0")
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).hasSize(1)
    }

    @Test
    fun `When a stopwatch is running then its value should be updated`() = runBlockingTest {
        givenStateHolderReturnsTime("0", "1", "2", "3", "4", "5")
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result[TASK1]).isEqualTo("5")
    }

    @Test
    fun `When a stopwatch is paused then its value should not change`() = runBlockingTest {
        givenStateHolderReturnsTime("0", "1", "2", "3", "4", "5")
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(50)
        systemUnderTest.pause(task = TASK1)

        val result = systemUnderTest.ticker.first()

        expectThat(result[TASK1]).isEqualTo("1")
    }

    @Test
    fun `When a stopwatch is stopped it is removed from the list`() = runBlockingTest {
        givenStateHolderReturnsTime("0")
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)
        systemUnderTest.stop(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result[TASK1]).isNull()
    }

    @Test
    fun `Multiple stopwatches can run in parallel`() = runBlockingTest {
        givenStateHolderReturnsTime("0", "1", "2", "3", "4", "5")
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK2)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result[TASK1]).isEqualTo("5")
        expectThat(result[TASK2]).isEqualTo("5")
    }

    @Nested
    inner class Coroutines {

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

    private fun givenStateHolderReturnsTime(vararg timeString: String) {
        every { stopwatchStateHolder.getStringTimeRepresentation() } returnsMany listOf(*timeString)
    }
}
