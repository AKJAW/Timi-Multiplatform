package com.akjaw.task.list.view

import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder
import com.akjaw.task.list_api.view.TaskListScreenCreator
import javax.inject.Inject

internal class TaskListScreenCreatorImpl @Inject constructor(): TaskListScreenCreator {

    @Composable
    override fun Create(navigationHolder: NavigationHolder) {
        TaskListScreen(navController = navigationHolder.controller)
    }
}
