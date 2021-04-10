package com.example.timicompose.stopwatch.domain

import androidx.compose.ui.graphics.Color
import com.akjaw.core.common.domain.model.Task
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.example.timicompose.stopwatch.domain.model.StopwatchState
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
        private val TASK1 = Task("First task", Color.White, false)
        private val TASK2 = Task("Second the cooler task", Color.White, false)
    }

    private val stopwatchStateHolder: StopwatchStateHolder = mockk(relaxed = true) {
        every { getStringTimeRepresentation() } returns  ""
    }
    private val stopwatchStateHolderFactory: StopwatchStateHolderFactory = mockk {
        every { create() } returns stopwatchStateHolder
    }
    val coroutineDispatcher = TestCoroutineDispatcher()
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
    fun `After starting a stopwatch it is present in the list`() = runBlockingTest {
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
    fun `Starting a stopwatch multiple times is not reflected in the list`() = runBlockingTest {
        givenStateHolderReturnsTime("0")
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).hasSize(1)
    }

    @Test
    fun `When a stopwatch is running its value update is reflected in the list`() = runBlockingTest {
        givenStateHolderReturnsTime("0", "1", "2", "3", "4", "5")
        systemUnderTest.start(task = TASK1)
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result[TASK1]).isEqualTo("5")
    }

    @Test
    fun `When a stopwatch is paused its value does not change`() = runBlockingTest {
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
    fun `After destroying the list is empty`() = runBlockingTest {
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK2)
        systemUnderTest.destroy()
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEmpty()
    }

    @Nested
    inner class Coroutines {

        @Test
        fun `Initially the scope is inactive`(){
            coroutineDispatcher.advanceTimeBy(1000)

            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `When first task is stared the scope becomes active`(){
            systemUnderTest.start(task = TASK1)

            coroutineDispatcher.advanceTimeBy(1000)
            expectThat(coroutineScope).hasChildrenCount(1)
        }

        @Test
        fun `When last task is stopped the scope becomes inactive`() {
            systemUnderTest.start(task = TASK1)
            coroutineDispatcher.advanceTimeBy(1000)

            systemUnderTest.stop(task = TASK1)

            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `Starting another stopwatch after stopping the last works correctly`() {
            systemUnderTest.start(task = TASK1)
            coroutineDispatcher.advanceTimeBy(1000)
            systemUnderTest.stop(task = TASK1)

            systemUnderTest.start(task = TASK1)

            expectThat(coroutineScope).hasChildrenCount(1)
        }

        @Test
        fun `When every stopwatch is paused the scope becomes inactive`() {
            every { stopwatchStateHolder.currentState }
                .returns(StopwatchState.Paused(0.toTimestampMilliseconds()))
            systemUnderTest.start(task = TASK1)

            systemUnderTest.pause(task = TASK1)


            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `Resuming after every stopwatch is paused makes the scope active`() {
            every { stopwatchStateHolder.currentState }
                .returns(StopwatchState.Paused(0.toTimestampMilliseconds()))
            systemUnderTest.start(task = TASK1)
            systemUnderTest.pause(task = TASK1)

            systemUnderTest.start(task = TASK1)

            expectThat(coroutineScope).hasChildrenCount(1)
        }

        @Test
        fun `After destroying the scope is not active`() {
            systemUnderTest.start(task = TASK1)
            coroutineDispatcher.advanceTimeBy(1000)

            systemUnderTest.destroy()

            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `After destroying it is possible to start another stopwatch`() {
            systemUnderTest.start(task = TASK1)
            coroutineDispatcher.advanceTimeBy(1000)
            systemUnderTest.destroy()

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
