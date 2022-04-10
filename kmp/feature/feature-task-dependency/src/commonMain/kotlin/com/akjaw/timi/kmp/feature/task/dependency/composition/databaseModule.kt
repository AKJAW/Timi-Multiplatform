package com.akjaw.timi.kmp.feature.task.dependency.composition

import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.database.TimiDatabase
import com.akjaw.timi.kmp.feature.task.dependency.database.databasePlatformModule
import com.akjaw.timi.kmp.feature.task.dependency.list.data.taskColorAdapter
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
