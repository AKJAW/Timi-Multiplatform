package com.akjaw.task.list.view

import androidx.compose.runtime.Composable
import com.akjaw.timi.android.core.view.NavigationHolder

//
interface TaskListScreenCreator {

    @Composable
    fun Create(navigationHolder: NavigationHolder)
}
