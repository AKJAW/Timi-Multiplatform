package com.akjaw.task.list

import com.akjaw.timi.kmp.feature.task.api.AddTask
import com.akjaw.timi.kmp.feature.task.api.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.GetTasks
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.AddTaskToDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.DeleteTasksFromDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.GetTasksFromDatabase

class DatabaseInteractorFactory(private val queries: TaskEntityQueries) {

    fun createGetTasks(): GetTasks =
        GetTasksFromDatabase(queries)

    fun createAddTask(): AddTask =
        AddTaskToDatabase(queries)

    fun createDeleteTasks(): DeleteTasks =
        DeleteTasksFromDatabase(queries)
}
