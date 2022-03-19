package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.data.AddTask
import com.akjaw.task.api.domain.Task
import javax.inject.Inject

internal class AddTaskToDatabase @Inject constructor(
    private val taskEntityQueries: TaskEntityQueries
) : AddTask {

    override suspend fun execute(task: Task) {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = task.name,
            color = task.backgroundColor.argb,
        )
    }
}
