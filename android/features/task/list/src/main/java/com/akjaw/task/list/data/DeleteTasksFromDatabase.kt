package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.api.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.model.Task

internal class DeleteTasksFromDatabase (
    private val taskEntityQueries: TaskEntityQueries,
) : DeleteTasks {

    override suspend fun execute(tasks: List<Task>) {
        taskEntityQueries.transaction {
            tasks.forEach {
                taskEntityQueries.deleteTaskById(it.id)
            }
        }
    }
}
