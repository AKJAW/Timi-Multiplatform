package com.akjaw.details.composition

import com.akjaw.details.view.TaskDetailScreenCreatorImpl
import com.akjaw.task.api.view.TaskDetailScreenCreator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TaskDetailsModule {

    @Binds
    abstract fun bindTaskDetailScreenCreator(creator: TaskDetailScreenCreatorImpl): TaskDetailScreenCreator
}
