package com.akjaw.task.list.data

import androidx.compose.ui.graphics.Color
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.data.GetTasks
import com.akjaw.task.api.domain.Task
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetTasksFromDatabase @Inject constructor(
    private val taskEntityQueries: TaskEntityQueries,
) : GetTasks {

    override fun execute(): Flow<List<Task>> =
        taskEntityQueries.selectAllTasks {
            id: Long,
            position: Long,
            name: String,
            color: Int ->
            Task(id, name, Color(color), false)
        }.asFlow().mapToList()
}
