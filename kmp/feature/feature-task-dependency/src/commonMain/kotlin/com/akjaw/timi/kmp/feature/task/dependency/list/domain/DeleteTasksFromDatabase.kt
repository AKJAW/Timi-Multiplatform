package com.akjaw.timi.kmp.feature.task.dependency.list.domain

import com.akjaw.timi.kmp.feature.task.api.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries

// TODO make internal
class DeleteTasksFromDatabase(
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
