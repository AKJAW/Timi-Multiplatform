package com.akjaw.timicompose.composition

import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.Database
import com.akjaw.task.list.data.taskColorAdapter
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val testModule = module {
    single { TimestampProviderStub() }
    single<TimestampProvider> { get<TimestampProviderStub>() }
    // TODO datbase in memory?
    single<SqlDriver> { AndroidSqliteDriver(Database.Schema, androidContext(), "test-task.db") }
    single<Database> {
        Database(
            driver = get(),
            TaskEntityAdapter = taskColorAdapter
        )
    }
    single<TaskEntityQueries> { get<Database>().taskEntityQueries }
}
