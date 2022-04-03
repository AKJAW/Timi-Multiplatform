package com.akjaw.timi.kmp.feature.task.dependency.list.domain

import com.akjaw.timi.kmp.feature.task.api.GetTasks
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.api.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class GetTasksFromDatabase(
    private val taskEntityQueries: TaskEntityQueries,
) : GetTasks {

    override fun execute(): Flow<List<Task>> =
        taskEntityQueries.selectAllTasks { id: Long,
                                           position: Long,
                                           name: String,
                                           color: TaskColor ->
            Task(
                id = id,
                name = name,
                backgroundColor = color,
                isSelected = false
            )
        }.asFlow().mapToList()
}
