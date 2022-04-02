package com.akjaw.core.common.composition

import com.akjaw.core.common.domain.ActivityInitializer
import com.akjaw.core.common.domain.ActivityInitializerHolder
import org.koin.dsl.module

val androidCoreModule = module {
    single {
        val initializers = getAll<ActivityInitializer>().distinct()
        ActivityInitializerHolder(initializers)
    }
}
