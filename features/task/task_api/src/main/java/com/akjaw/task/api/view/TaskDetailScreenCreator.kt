package com.akjaw.task.api.view

import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder

interface TaskDetailScreenCreator {

    @Composable
    fun Create(navigationHolder: NavigationHolder)
}
