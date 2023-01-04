package com.akjaw.timi.android.app.composition

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.test.time.StubTimestampProvider
import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.akjaw.timi.kmp.feature.database.adapter.taskColorAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val testModule = module {
    single { StubTimestampProvider() }
    single<TimestampProvider> { get<StubTimestampProvider>() }
    // TODO datbase in memory?
    single<SqlDriver> { AndroidSqliteDriver(TimiDatabase.Schema, androidContext(), "test-task.db") }
    single<TimiDatabase> {
        TimiDatabase(
            driver = get(),
            TaskEntityAdapter = taskColorAdapter
        )
    }
    single<TaskEntityQueries> { get<TimiDatabase>().taskEntityQueries }
}
