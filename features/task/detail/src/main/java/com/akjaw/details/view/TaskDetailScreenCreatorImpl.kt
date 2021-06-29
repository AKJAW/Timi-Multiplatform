package com.akjaw.details.view

import androidx.compose.runtime.Composable
import com.akjaw.task.api.view.TaskDetailScreenCreator
import javax.inject.Inject

internal class TaskDetailScreenCreatorImpl @Inject constructor() : TaskDetailScreenCreator {

    @Composable
    override fun Create() {
        TaskDetailScreen()
    }
}
