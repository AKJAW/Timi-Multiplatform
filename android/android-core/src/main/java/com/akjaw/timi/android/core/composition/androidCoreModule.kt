package com.akjaw.timi.android.core.composition

import com.akjaw.timi.android.core.domain.ActivityInitializer
import com.akjaw.timi.android.core.domain.ActivityInitializerHolder
import org.koin.dsl.module

val androidCoreModule = module {
    single {
        val initializers = getAll<ActivityInitializer>().distinct()
        ActivityInitializerHolder(initializers)
    }
}
