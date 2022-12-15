package com.akjaw.timi.kmp.feature.task.dependency.detail.composition

import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import org.koin.dsl.module

val taskDetailsModule = module {
    factory { CalendarDaysCalculator() }
    factory {
        CalendarViewModel(get(), get())
    }
}
