@file:JvmName("TaskListModuleKt")

package com.akjaw.task.list.composition

import android.content.Context
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.Database
import com.akjaw.task.list.data.AddTaskToDatabase
import com.akjaw.task.list.data.DeleteTasksFromDatabase
import com.akjaw.task.list.data.GetTasksFromDatabase
import com.akjaw.timi.kmp.feature.task.domain.AddTask
import com.akjaw.timi.kmp.feature.task.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import javax.inject.Singleton

val taskListModule = module {
    single<SqlDriver> { AndroidSqliteDriver(Database.Schema, androidContext(), "task.db") }
    single<Database> { Database(get()) }
    single<TaskEntityQueries> { get<Database>().taskEntityQueries }
    factory<AddTask> { AddTaskToDatabase(get()) }
    factory<DeleteTasks> { DeleteTasksFromDatabase(get()) }
    factory<GetTasks> { GetTasksFromDatabase(get()) }
}
