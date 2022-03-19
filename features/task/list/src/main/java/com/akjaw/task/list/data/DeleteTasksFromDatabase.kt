package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import javax.inject.Inject

internal class DeleteTasksFromDatabase @Inject constructor(
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
