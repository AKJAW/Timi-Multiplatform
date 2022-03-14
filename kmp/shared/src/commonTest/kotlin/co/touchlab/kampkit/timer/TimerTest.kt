package co.touchlab.kampkit.timer

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test

class TimerTest {

    private val standardTestDispatcher = StandardTestDispatcher()
    private lateinit var sut: Timer

    @BeforeTest
    fun setUp() {
        sut = Timer(CoroutineScope(standardTestDispatcher))
    }

    @Test
    fun `At start time equal to 00 00`() {
        sut.timeString.value shouldBe "00:00"
    }

    @Test
    fun `After one second time equal to 00 01`() {
        sut.start()

        standardTestDispatcher.scheduler.advanceTimeBy(1001)

        sut.timeString.value shouldBe "00:01"
    }

    @Test
    fun `After one minute time equal to 01 00`() {
        sut.start()

        standardTestDispatcher.scheduler.advanceTimeBy(60 * 1001)

        sut.timeString.value shouldBe "01:00"
    }

    @Test
    fun `When paused the time does not update`() {
        sut.start()
        standardTestDispatcher.scheduler.advanceTimeBy(1001)
        sut.stop()

        standardTestDispatcher.scheduler.advanceTimeBy(60 * 1001)

        sut.timeString.value shouldBe "00:01"
    }
}
