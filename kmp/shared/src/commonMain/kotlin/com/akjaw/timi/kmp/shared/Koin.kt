package com.akjaw.timi.kmp.shared

import com.akjaw.timi.kmp.core.shared.composition.coreSharedModule
import com.akjaw.timi.kmp.feature.database.composition.databaseModule
import com.akjaw.timi.kmp.feature.stopwatch.composition.stopwatchModule
import com.akjaw.timi.kmp.feature.task.dependency.detail.composition.taskDetailsModule
import com.akjaw.timi.kmp.feature.task.dependency.list.composition.taskListModule
import org.koin.core.module.Module

val kmmKoinModules: List<Module> = listOf(
    coreSharedModule,
    stopwatchModule,
    databaseModule,
    taskListModule,
    taskDetailsModule
)
