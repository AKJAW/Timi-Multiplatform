package com.akjaw.timi.kmp.feature.task.dependency.detail.presenter

import app.cash.turbine.test
import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.test.task.FakeTimeEntryRepository
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.CommonTaskDetailViewModel
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class CommonTaskDetailViewModelTest {

    companion object {
        private const val TASK_ID = 2L
        private val TIME_ENTRY = createTimeEntry(1, 20_000, CalendarDay(28, 12, 2022))

        // TODO extract to core-test?
        private fun createTimeEntry(id: Long, amount: Long = 0, calendarDay: CalendarDay = CalendarDay(0)) =
            TimeEntry(id, TASK_ID, TimestampMilliseconds(amount), calendarDay)
    }

    private lateinit var fakeTimeEntryRepository: FakeTimeEntryRepository
    private lateinit var systemUnderTest: CommonTaskDetailViewModel

    @BeforeTest
    fun setUp() {
        fakeTimeEntryRepository = FakeTimeEntryRepository()
        systemUnderTest = CommonTaskDetailViewModel(TASK_ID, fakeTimeEntryRepository)
    }

    @Test
    fun `Correctly returns time entries for that given day`() = runTest {
        val firstTimeEntry = TIME_ENTRY
        val secondTimeEntry = TIME_ENTRY.copy(date = CalendarDay(29, 12, 2022))
        fakeTimeEntryRepository.setEntry(TASK_ID, listOf(firstTimeEntry, secondTimeEntry))

        systemUnderTest.getTimeEntries(secondTimeEntry.date).test {
            awaitItem() shouldBe listOf(secondTimeEntry)
        }
    }

    @Test
    fun `Correctly inserts an entry`() = runTest {
        val timeAmount = TimestampMilliseconds(20_000)
        val day = CalendarDay(20, 12, 2022)

        systemUnderTest.addTimeEntry(timeAmount, day)

        val result: List<TimeEntry>? = fakeTimeEntryRepository.getEntry(TASK_ID)
        assertSoftly(result) {
            shouldNotBeNull()
            shouldHaveSize(1)
            first().taskId shouldBe TASK_ID
            first().date shouldBe day
            first().timeAmount shouldBe timeAmount
        }
    }

    @Test
    fun `Correctly removes an entry`() = runTest {
        fakeTimeEntryRepository.setEntry(TASK_ID, listOf(TIME_ENTRY))

        systemUnderTest.removeTimeEntry(TIME_ENTRY.id)

        val result: List<TimeEntry>? = fakeTimeEntryRepository.getEntry(TASK_ID)
        assertSoftly(result) {
            shouldNotBeNull()
            shouldBeEmpty()
        }
    }
}
