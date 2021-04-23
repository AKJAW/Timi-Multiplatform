package com.akjaw.task.list

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.api.data.AddTask
import com.akjaw.task.api.data.DeleteTasks
import com.akjaw.task.api.data.GetTasks
import com.akjaw.task.list.data.AddTaskToDatabase
import com.akjaw.task.list.data.DeleteTasksFromDatabase
import com.akjaw.task.list.data.GetTasksFromDatabase

class DatabaseInteractorFactory(private val queries: TaskEntityQueries) {

    fun createGetTasks(): GetTasks = GetTasksFromDatabase(queries)

    fun createAddTask(): AddTask = AddTaskToDatabase(queries)

    fun createDeleteTasks(): DeleteTasks = DeleteTasksFromDatabase(queries)
}
