@file:JvmName("TaskListModuleKt")

package com.akjaw.timi.android.feature.task.list.ui.composition

import com.akjaw.timi.android.feature.task.list.ui.view.TaskListScreenCreator
import com.akjaw.timi.android.feature.task.list.ui.view.TaskListScreenCreatorImpl
import org.koin.dsl.module

val taskUiModule = module {
    factory<TaskListScreenCreator> { TaskListScreenCreatorImpl() }
}
