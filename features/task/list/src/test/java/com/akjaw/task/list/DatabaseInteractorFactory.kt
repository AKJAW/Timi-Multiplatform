package com.akjaw.task.list

import com.akjaw.task.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.domain.AddTask
import com.akjaw.timi.kmp.feature.task.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.akjaw.task.list.data.AddTaskToDatabase
import com.akjaw.task.list.data.DeleteTasksFromDatabase
import com.akjaw.task.list.data.GetTasksFromDatabase

class DatabaseInteractorFactory(private val queries: TaskEntityQueries) {

    fun createGetTasks(): GetTasks = GetTasksFromDatabase(queries)

    fun createAddTask(): AddTask = AddTaskToDatabase(queries)

    fun createDeleteTasks(): DeleteTasks = DeleteTasksFromDatabase(queries)
}
