package com.akjaw.details.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder
import com.akjaw.task.api.view.TaskDetailScreenCreator
import javax.inject.Inject

internal class TaskDetailScreenCreatorImpl @Inject constructor() : TaskDetailScreenCreator {

    @ExperimentalMaterialApi
    @Composable
    override fun Create(navigationHolder: NavigationHolder) {
        TaskDetailScreen(navigationHolder.controller)
    }
}
