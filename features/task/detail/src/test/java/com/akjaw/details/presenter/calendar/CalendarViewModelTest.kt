package com.akjaw.details.presenter.calendar

import app.cash.turbine.test
import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class CalendarViewModelTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var timestampProviderStub: TimestampProviderStub
    private lateinit var systemUnderTest: CalendarViewModel

    @BeforeEach
    fun setUp() {
        timestampProviderStub = TimestampProviderStub()
    }

    @Test
    fun `Initially the system timestamp is used for the month name`() = runBlocking {
        timestampProviderStub.value = 1625495425.toTimestampMilliseconds()

        systemUnderTest = createViewModel()

        systemUnderTest.currentMonth.test {
            expectThat(expectItem().monthName).isEqualTo("July")
        }
    }

    @Test
    fun `Initially the system timestamp is used for the month days`() = runBlocking {
        timestampProviderStub.value = 1625495425.toTimestampMilliseconds()

        systemUnderTest = createViewModel()

        systemUnderTest.currentMonth.test {
            expectThat(expectItem().dayRows).isEqualTo(
                listOf(
                    listOf(28..30, 1..4).toDays(),
                    listOf(5..11).toDays(),
                    listOf(12..18).toDays(),
                    listOf(19..25).toDays(),
                    listOf(26..31, 1..6).toDays(),
                )
            )
        }
    }

    private fun createViewModel() = CalendarViewModel(
        backgroundDispatcher = testCoroutineDispatcher,
    )
}

class TimestampProviderStub : TimestampProvider {

    var value: TimestampMilliseconds = 0.toTimestampMilliseconds()

    override fun getMilliseconds(): TimestampMilliseconds = value
}
