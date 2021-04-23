package com.akjaw.task.list.data

import androidx.compose.ui.graphics.toArgb
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.data.AddTask
import com.akjaw.task.api.domain.Task
import javax.inject.Inject

internal class AddTaskToDatabase @Inject constructor(
    private val taskEntityQueries: TaskEntityQueries
) : AddTask {

    override fun execute(task: Task) {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = task.name,
            color = task.backgroundColor.toArgb()
        )
    }
}
