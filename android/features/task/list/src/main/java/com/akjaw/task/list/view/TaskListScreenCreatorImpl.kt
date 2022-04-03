package com.akjaw.task.list.view

import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder

internal class TaskListScreenCreatorImpl () : TaskListScreenCreator {

    @Composable
    override fun Create(navigationHolder: NavigationHolder) {
        TaskListScreen(navController = navigationHolder.controller)
    }
}
