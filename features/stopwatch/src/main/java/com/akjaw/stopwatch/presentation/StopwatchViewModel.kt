package com.akjaw.stopwatch.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class StopwatchViewModel @Inject constructor(
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {

    val stopwatchValue: StateFlow<String> = stopwatchListOrchestrator.ticker

    fun start() = stopwatchListOrchestrator.start()

    fun pause() = stopwatchListOrchestrator.pause()

    fun stop() = stopwatchListOrchestrator.stop()
}
