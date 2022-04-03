package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.api.AddTask
import com.akjaw.timi.kmp.feature.task.api.model.Task

internal class AddTaskToDatabase (
    private val taskEntityQueries: TaskEntityQueries
) : AddTask {

    override suspend fun execute(task: Task) {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = task.name,
            color = task.backgroundColor,
        )
    }
}
