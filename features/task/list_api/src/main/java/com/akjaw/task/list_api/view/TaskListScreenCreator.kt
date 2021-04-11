package com.akjaw.task.list_api.view

import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder

interface TaskListScreenCreator {

    @Composable
    fun Create(navigationHolder: NavigationHolder)
}
