package com.akjaw.task.list.composition

import android.content.Context
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskListDatabaseModule: KoinComponent {

    @Provides
    fun provideTaskEntityQueries(): TaskEntityQueries = get()
}
