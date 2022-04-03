@file:JvmName("TaskListModuleKt")

package com.akjaw.task.list.composition

import com.akjaw.task.list.presentation.TaskListViewModel
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
import com.akjaw.task.list.view.TaskListScreenCreator
import com.akjaw.task.list.view.TaskListScreenCreatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskUiModule = module {
    factory<TaskListScreenCreator> { TaskListScreenCreatorImpl() }
    factory { TaskSelectionTrackerFactory() }
    viewModel {
        TaskListViewModel(get(), get(), get(), get(), get())
    }
}

