package com.akjaw.timi.android.feature.task.list.ui.view

import androidx.compose.runtime.Composable
import com.akjaw.timi.android.core.view.NavigationHolder

internal class TaskListScreenCreatorImpl() : TaskListScreenCreator {

    @Composable
    override fun Create(navigationHolder: NavigationHolder) {
        TaskListScreen(navController = navigationHolder.controller)
    }
}