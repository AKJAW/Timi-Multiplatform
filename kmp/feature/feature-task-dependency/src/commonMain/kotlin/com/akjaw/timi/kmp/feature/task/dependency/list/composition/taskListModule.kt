package com.akjaw.timi.kmp.feature.task.dependency.list.composition

import com.akjaw.timi.kmp.feature.task.api.list.domain.AddTask
import com.akjaw.timi.kmp.feature.task.api.list.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.list.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.api.list.presentation.TaskListViewModel
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.AddTaskToDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.DeleteTasksFromDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.domain.GetTasksFromDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.CommonTaskListViewModel
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTrackerFactory
import org.koin.dsl.module

val taskListModule = module {
    factory { TaskSelectionTrackerFactory() }
    factory<TaskListViewModel> {
        CommonTaskListViewModel(get(), get(), get(), get(), get())
    }
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
