package com.akjaw.task.list.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.data.TaskRepository
import com.akjaw.task.api.domain.Task
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class TaskRepositoryImpl @Inject constructor(
    private val taskEntityQueries: TaskEntityQueries
): TaskRepository {

    override val tasks: Flow<List<Task>> = taskEntityQueries.selectAllTasks {
            id: Long,
            position: Long,
            name: String,
            color: Int ->
        Task(name, Color(color), false, id)
    }.asFlow().mapToList()

    override fun addTask(taskToBeAdded: Task) {
        taskEntityQueries.insertTask(
            id = null,
            position = 0,
            name = taskToBeAdded.name,
            color = taskToBeAdded.backgroundColor.toArgb()
        )
    }

    override fun deleteTasks(tasksToBeDeleted: List<Task>) {
        taskEntityQueries.transaction {
            tasksToBeDeleted.forEach {
                taskEntityQueries.deleteTaskById(it.id)
            }
        }
    }
}
