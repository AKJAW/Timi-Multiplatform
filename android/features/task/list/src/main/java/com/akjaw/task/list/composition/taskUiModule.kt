@file:JvmName("TaskListModuleKt")

package com.akjaw.task.list.composition

import com.akjaw.task.list.view.TaskListScreenCreator
import com.akjaw.task.list.view.TaskListScreenCreatorImpl
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.TaskListViewModel
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTrackerFactory
import org.koin.dsl.module

val taskUiModule = module {
    factory<TaskListScreenCreator> { TaskListScreenCreatorImpl() }
    factory { TaskSelectionTrackerFactory() }
    factory {
        // TODO create scope?
        TaskListViewModel(get(), get(), get(), get(), get())
    }
}

