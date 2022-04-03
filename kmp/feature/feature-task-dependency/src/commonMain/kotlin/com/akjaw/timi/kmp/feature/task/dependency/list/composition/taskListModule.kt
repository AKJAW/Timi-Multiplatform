package com.akjaw.timi.kmp.feature.task.dependency.list.composition

import com.akjaw.timi.kmp.feature.task.api.AddTask
import com.akjaw.timi.kmp.feature.task.api.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.GetTasks
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.database.TimiDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.data.taskColorAdapter
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.AddTaskToDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.DeleteTasksFromDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.GetTasksFromDatabase
import org.koin.dsl.module

val taskListModule = module {
    single<TimiDatabase> {
        TimiDatabase(
            get(),
            taskColorAdapter
        )
    }
    single<TaskEntityQueries> { get<TimiDatabase>().taskEntityQueries }
    factory<AddTask> { AddTaskToDatabase(get()) }
    factory<DeleteTasks> {
        DeleteTasksFromDatabase(
            get()
        )
    }
    factory<GetTasks> {
        GetTasksFromDatabase(
            get()
        )
    }
}
