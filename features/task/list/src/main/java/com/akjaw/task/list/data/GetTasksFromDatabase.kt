package com.akjaw.task.list.data

import com.akjaw.task.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import com.akjaw.timi.kmp.feature.task.domain.model.TaskColor
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
            color: TaskColor ->
            Task(
                id = id,
                name = name,
                backgroundColor = color,
                isSelected = false
            )
        }.asFlow().mapToList()
}
