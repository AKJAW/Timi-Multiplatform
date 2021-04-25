package com.akjaw.task.list.composition

import com.akjaw.task.api.view.TaskListScreenCreator
import com.akjaw.task.list.view.TaskListScreenCreatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class, InteractorsModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class TaskListModule {

    @Binds
    abstract fun bindTaskListScreenCreator(creator: TaskListScreenCreatorImpl): TaskListScreenCreator
}
