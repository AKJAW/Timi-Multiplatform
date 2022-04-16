package com.akjaw.timi.android.task.list.ui.view

import androidx.compose.runtime.Composable
import com.akjaw.timi.android.core.view.NavigationHolder

//
interface TaskListScreenCreator {

    @Composable
    fun Create(navigationHolder: NavigationHolder)
}
