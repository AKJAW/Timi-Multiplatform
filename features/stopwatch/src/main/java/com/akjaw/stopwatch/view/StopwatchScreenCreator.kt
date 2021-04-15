package com.akjaw.stopwatch.view

import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder

interface StopwatchScreenCreator {

    @Composable
    fun Create(navigationHolder: NavigationHolder)
}
