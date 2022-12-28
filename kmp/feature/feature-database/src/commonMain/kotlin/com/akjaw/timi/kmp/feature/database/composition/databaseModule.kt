package com.akjaw.timi.kmp.feature.database.composition

import com.akjaw.timi.kmp.feature.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.akjaw.timi.kmp.feature.database.adapter.taskColorAdapter
import org.koin.dsl.module

val databaseModule = module {
    databasePlatformModule()
    single<TimiDatabase> {
        TimiDatabase(
            get(),
            taskColorAdapter
        )
    }
    single<TaskEntityQueries> { get<TimiDatabase>().taskEntityQueries }
}
