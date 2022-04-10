package com.akjaw.timi.kmp.feature.stopwatch.domain.orchestrator

import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchStateHolderFactory
import com.akjaw.timi.kmp.feature.stopwatch.domain.helpers.FakeTimestampProvider
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.feature.task.api.model.Task
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
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
    fun `Initially the stopwatch list is empty`() {
        advanceTime(1000)

        val result = systemUnderTest.ticker.value

        result.shouldBeEmpty()
    }

    @Test
    fun `When a stopwatch is started then it should be present in the list`() {
        systemUnderTest.start(task = TASK1)
        advanceTime(1000)

        val result = systemUnderTest.ticker.value

        assertSoftly {
            result shouldHaveSize 1
            val timeString = result[TASK1]
            timeString shouldBe "00:00:000"
        }
    }

    @Test
    fun `When a stopwatch is started multiple times then it should not be duplicated`() {
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK1)
        advanceTime(1000)

        val result = systemUnderTest.ticker.value

        result shouldHaveSize 1
    }

    @Test
    fun `When a stopwatch is running then its value is updated every 20 milliseconds`() {
        givenTimestamp(0)
        systemUnderTest.start(task = TASK1)

        givenTimestamp(1)
        advanceTime(20)
        systemUnderTest.ticker.value[TASK1] shouldBe "00:00:001"

        givenTimestamp(2)
        advanceTime(20)
        systemUnderTest.ticker.value[TASK1] shouldBe "00:00:002"

        givenTimestamp(3)
        advanceTime(20)
        systemUnderTest.ticker.value[TASK1] shouldBe "00:00:003"
    }

    @Test
    fun `When a stopwatch is paused then its value should not change`() {
        givenTimestamp(0)
        systemUnderTest.start(task = TASK1)
        givenTimestamp(1)
        advanceTime(20)

        systemUnderTest.pause(task = TASK1)
        givenTimestamp(2)
        advanceTime(1000)

        val result = systemUnderTest.ticker.value
        result[TASK1] shouldBe "00:00:001"
    }

    @Test
    fun `When a stopwatch is stopped it is removed from the list`() {
        systemUnderTest.start(task = TASK1)
        advanceTime(1000)
        systemUnderTest.stop(task = TASK1)
        advanceTime(1000)

        val result = systemUnderTest.ticker.value

        result[TASK1].shouldBeNull()
    }

    @Test
    fun `Multiple stopwatches can run in parallel`() {
        givenTimestamp(0)
        systemUnderTest.start(task = TASK1)
        systemUnderTest.start(task = TASK2)
        givenTimestamp(1)
        advanceTime(20)

        val result = systemUnderTest.ticker.value

        result[TASK1] shouldBe "00:00:001"
        result[TASK2] shouldBe "00:00:001"
    }

    private fun givenTimestamp(time: Int) {
        fakeTimestampProvider.givenTimePassed(time.toLong())
    }

    private fun advanceTime(amount: Long) {
        coroutineDispatcher.scheduler.advanceTimeBy(amount)
    }
}
