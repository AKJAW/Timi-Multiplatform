package com.example.timicompose.stopwatch.domain

import android.util.Log
import com.example.timicompose.stopwatch.domain.model.StopwatchState
import com.example.timicompose.stopwatch.domain.model.TimestampMilliseconds
import com.example.timicompose.stopwatch.domain.model.toTimestampMilliseconds
import com.soywiz.klock.DateTime
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class StopwatchStateHolder constructor(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

    //TODO does this have to be thread-safe?
    private var currentState: StopwatchState = StopwatchState.Paused(0.toTimestampMilliseconds())
    var job: Job? = null

    fun start(tickBlock: (String) -> Unit) {
        if (currentState is StopwatchState.Running) return
        val newState = stopwatchStateCalculator.calculateRunningState(currentState)

        job = GlobalScope.launch(Dispatchers.Default) {
            currentState = newState
            while (true) {
                val time = measureTimeMillis {
                    val elapsedTime = elapsedTimeCalculator.calculate(newState)
                    val timerString = format(elapsedTime)
                    tickBlock(timerString)
                    delay(20)
                }
                Log.d("TIMEEEEE", time.toString())
            }
        }
    }

    private fun format(timestampMilliseconds: TimestampMilliseconds): String {
        val dateTime = DateTime(timestampMilliseconds.value)
        return "${dateTime.minutes}:${dateTime.seconds}:${dateTime.milliseconds}"
    }

    fun pause() {
        if (currentState is StopwatchState.Paused) return
        val newState = stopwatchStateCalculator.calculatePausedState(currentState)

        job?.cancel()
        job = null

        currentState = newState
    }
}
