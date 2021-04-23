package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.data.DeleteTasks
import com.akjaw.task.api.domain.Task
import javax.inject.Inject

internal class DeleteTasksFromDatabase @Inject constructor(
    private val taskEntityQueries: TaskEntityQueries
) : DeleteTasks {

    override fun execute(tasks: List<Task>) {
        taskEntityQueries.transaction {
            tasks.forEach {
                taskEntityQueries.deleteTaskById(it.id)
            }
        }
    }
}
