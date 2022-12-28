package com.akjaw.timi.kmp.feature.task.dependency.list.domain

import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.api.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task

internal class DeleteTasksFromDatabase(
    private val taskEntityQueries: TaskEntityQueries
) : DeleteTasks {

    override suspend fun execute(tasks: List<Task>) {
        taskEntityQueries.transaction {
            tasks.forEach {
                taskEntityQueries.deleteTaskById(it.id)
            }
        }
    }
}
