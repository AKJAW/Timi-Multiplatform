package com.example.timicompose.stopwatch.domain

import android.util.Log
import com.example.timicompose.common.domain.model.toTimestampMilliseconds
import com.example.timicompose.stopwatch.domain.model.StopwatchState
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
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
                    val timerString = timestampMillisecondsFormatter.format(elapsedTime)
                    tickBlock(timerString)
                    delay(20)
                }
                Log.d("TIMEEEEE", time.toString())
            }
        }
    }

    fun pause() {
        if (currentState is StopwatchState.Paused) return
        val newState = stopwatchStateCalculator.calculatePausedState(currentState)

        job?.cancel()
        job = null

        currentState = newState
    }
}
