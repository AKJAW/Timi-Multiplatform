package com.akjaw.timi.android.feature.task.detail.ui.composition

import com.akjaw.timi.android.feature.task.detail.ui.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.android.feature.task.detail.ui.presentation.calendar.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskDetailsModule = module {
    factory { CalendarDaysCalculator() }
    viewModel {
        CalendarViewModel(get(), get())
    }
}
