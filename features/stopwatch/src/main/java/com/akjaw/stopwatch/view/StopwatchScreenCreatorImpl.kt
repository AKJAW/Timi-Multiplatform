package com.akjaw.stopwatch.view

import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder
import javax.inject.Inject

internal class StopwatchScreenCreatorImpl @Inject constructor() : StopwatchScreenCreator {

    @Composable
    override fun Create(navigationHolder: NavigationHolder) {
        StopwatchScreen(navController = navigationHolder.controller)
    }
}
