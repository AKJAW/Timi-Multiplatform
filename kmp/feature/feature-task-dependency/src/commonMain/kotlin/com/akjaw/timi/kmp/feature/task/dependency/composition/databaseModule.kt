package com.akjaw.timi.kmp.feature.task.dependency.composition

import com.akjaw.timi.kmp.feature.task.dependency.database.databasePlatformModule
import org.koin.dsl.module

val databaseModule = module {
    databasePlatformModule()
}