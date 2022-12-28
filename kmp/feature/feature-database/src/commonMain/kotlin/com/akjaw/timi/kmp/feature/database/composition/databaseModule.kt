package com.akjaw.timi.kmp.feature.database.composition

import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.akjaw.timi.kmp.feature.database.adapter.taskColorAdapter
import com.akjaw.timi.kmp.feature.database.adapter.timestampMillisecondsAdapterAdapter
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

val databaseModule = module {
    databasePlatformModule()
    single<TimiDatabase> {
        createDatabase(get())
    }
    single<TaskEntityQueries> { get<TimiDatabase>().taskEntityQueries }
}

internal fun createDatabase(sqlDriver: SqlDriver): TimiDatabase {
    return TimiDatabase(
        sqlDriver,
        taskColorAdapter,
        timestampMillisecondsAdapterAdapter,
    )
}