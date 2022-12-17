package com.akjaw.timi.kmp.feature.task.dependency.list.domain

import com.akjaw.timi.kmp.feature.task.api.domain.AddTask
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries

internal class AddTaskToDatabase(
    private val taskEntityQueries: TaskEntityQueries
) : AddTask {

    override suspend fun execute(task: Task) {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = task.name,
            color = task.backgroundColor
        )
    }
}
