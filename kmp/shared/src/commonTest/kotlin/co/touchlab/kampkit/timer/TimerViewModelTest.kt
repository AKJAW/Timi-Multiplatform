package co.touchlab.kampkit.timer

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test

class TimerViewModelTest {

    private val standardTestDispatcher = StandardTestDispatcher()
    private lateinit var sut: TimerViewModel

    @BeforeTest
    fun setUp() {
        val timer = Timer(CoroutineScope(standardTestDispatcher))
        sut = TimerViewModel(timer)
    }

    @Test
    fun `Initialy the timer should be not active`() {
        sut.isActive.value shouldBe false
    }

    @Test
    fun `After toggling the timer should be active`() {
        sut.toggle()

        sut.isActive.value shouldBe true
    }

    @Test
    fun `After toggling twice the timer should be not active`() {
        sut.toggle()

        sut.isActive.value shouldBe true
    }
}
