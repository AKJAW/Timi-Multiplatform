@file:JvmName("TaskListModuleKt")

package com.akjaw.task.list.composition

import com.akjaw.task.list.view.TaskListScreenCreator
import com.akjaw.task.list.view.TaskListScreenCreatorImpl
import org.koin.dsl.module

val taskUiModule = module {
    factory<TaskListScreenCreator> { TaskListScreenCreatorImpl() }
}

