package com.akjaw.task.list.composition

import com.akjaw.task.api.data.AddTask
import com.akjaw.task.api.data.DeleteTasks
import com.akjaw.task.api.data.GetTasks
import com.akjaw.task.list.data.AddTaskToDatabase
import com.akjaw.task.list.data.DeleteTasksFromDatabase
import com.akjaw.task.list.data.GetTasksFromDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
internal abstract class InteractorsModule {

    @Binds
    abstract fun bindGetTasks(getTasks: GetTasksFromDatabase): GetTasks

    @Binds
    abstract fun bindAddTask(addTask: AddTaskToDatabase): AddTask

    @Binds
    abstract fun bindDeleteTasks(deleteTasks: DeleteTasksFromDatabase): DeleteTasks
}
