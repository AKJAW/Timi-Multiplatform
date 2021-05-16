package com.akjaw.stopwatch.domain

import com.akjaw.stopwatch.domain.model.StopwatchState
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
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

internal class StopwatchListOrchestratorTest {

    private val stopwatchStateHolder: StopwatchStateHolder = mockk(relaxed = true) {
        every { getStringTimeRepresentation() } returns ""
    }
    val coroutineDispatcher = TestCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private lateinit var systemUnderTest: StopwatchListOrchestrator

    @BeforeEach
    fun setUp() {
        systemUnderTest = StopwatchListOrchestrator(
            stopwatchStateHolder = stopwatchStateHolder,
            scope = coroutineScope
        )
    }

    @Test
    fun `Initially the stopwatch value is empty`() = runBlockingTest {
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEmpty()
    }

    @Test
    fun `After starting a stopwatch its value is emitted`() = runBlockingTest {
        givenStateHolderReturnsTime("0")
        systemUnderTest.start()
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEqualTo("0")
    }

    @Test
    fun `When a stopwatch is running its value updated accordingly`() = runBlockingTest {
        givenStateHolderReturnsTime("0", "1", "2", "3", "4", "5")
        systemUnderTest.start()
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEqualTo("5")
    }

    @Test
    fun `When a stopwatch is paused its value does not change`() = runBlockingTest {
        givenStateHolderReturnsTime("0", "1", "2", "3", "4", "5")
        systemUnderTest.start()
        coroutineDispatcher.advanceTimeBy(50)
        systemUnderTest.pause()

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEqualTo("2")
    }

    @Test
    fun `When a stopwatch is stopped the value is set to an empty string`() = runBlockingTest {
        givenStateHolderReturnsTime("0")
        systemUnderTest.start()
        coroutineDispatcher.advanceTimeBy(1000)
        systemUnderTest.stop()
        coroutineDispatcher.advanceTimeBy(1000)

        val result = systemUnderTest.ticker.first()

        expectThat(result).isEmpty()
    }

    @Nested
    inner class Coroutines {

        @Test
        fun `Initially the scope is inactive`() {
            coroutineDispatcher.advanceTimeBy(1000)

            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `When first task is stared the scope becomes active`() {
            systemUnderTest.start()

            coroutineDispatcher.advanceTimeBy(1000)
            expectThat(coroutineScope).hasChildrenCount(1)
        }

        @Test
        fun `When last task is stopped the scope becomes inactive`() {
            systemUnderTest.start()
            coroutineDispatcher.advanceTimeBy(1000)

            systemUnderTest.stop()

            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `Starting another stopwatch after stopping the last works correctly`() {
            systemUnderTest.start()
            coroutineDispatcher.advanceTimeBy(1000)
            systemUnderTest.stop()

            systemUnderTest.start()

            expectThat(coroutineScope).hasChildrenCount(1)
        }

        @Test
        fun `When every stopwatch is paused the scope becomes inactive`() {
            every { stopwatchStateHolder.currentState }
                .returns(StopwatchState.Paused(0))
            systemUnderTest.start()

            systemUnderTest.pause()

            expectThat(coroutineScope).hasChildrenCount(0)
        }

        @Test
        fun `Resuming after every stopwatch is paused makes the scope active`() {
            every { stopwatchStateHolder.currentState }
                .returns(StopwatchState.Paused(0))
            systemUnderTest.start()
            systemUnderTest.pause()

            systemUnderTest.start()

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
