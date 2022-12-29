package com.akjaw.timi.kmp.feature.task.dependency.detail.presenter

import app.cash.turbine.test
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.feature.task.dependency.detail.TimestampProviderStub
import com.akjaw.timi.kmp.feature.task.dependency.detail.createCalendarViewModel
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.DayViewState
import com.soywiz.klock.DateTime
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
internal class ViewModelMonthNameAndDaysTest {

    companion object {
        private val JULY_DATE_TIME = DateTime.createAdjusted(
            year = 2021,
            month = 7,
            day = 5
        )

        private const val CURRENT_MONTH_INDEX = CalendarViewModel.CURRENT_MONTH_INDEX
    }

    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeTest
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
        timestampProviderStub.value = JULY_DATE_TIME.unixMillisLong.toTimestampMilliseconds()
        systemUnderTest = createCalendarViewModel(timestampProviderStub)
    }

    @Test
    fun `The current month name is at the 40th index`() = runBlocking {
        systemUnderTest.viewState.test {
            awaitItem().months[CURRENT_MONTH_INDEX].monthName shouldBe "July"
        }
    }

    @Test
    fun `The previous month name is at the 39th index`() = runBlocking {
        systemUnderTest.viewState.test {
            awaitItem().months[39].monthName shouldBe "June"
        }
    }

    @Test
    fun `The next month name is at the 41th index`() = runBlocking {
        systemUnderTest.viewState.test {
            awaitItem().months[41].monthName shouldBe "August"
        }
    }

    @Test
    fun `The next year month name the year number in it`() = runBlocking {
        systemUnderTest.viewState.test {
            awaitItem().months[46].monthName shouldBe "January 2022"
        }
    }

    @Test
    fun `The previous year month name the year number in it`() = runBlocking {
        systemUnderTest.viewState.test {
            awaitItem().months[33].monthName shouldBe "December 2020"
        }
    }

    @Test
    fun `The current month first day is correct`() = runBlocking {
        systemUnderTest.viewState.test {
            val rows = awaitItem().months[CURRENT_MONTH_INDEX].calendarDayRows
            val firstDay = rows.first().first()
            firstDay shouldBe DayViewState(28, 6, 2021)
        }
    }

    @Test
    fun `The current month last day is correct`() = runBlocking {
        systemUnderTest.viewState.test {
            val rows = awaitItem().months[CURRENT_MONTH_INDEX].calendarDayRows
            val firstDay = rows.last().last()
            firstDay shouldBe DayViewState(8, 8, 2021)
        }
    }
}
