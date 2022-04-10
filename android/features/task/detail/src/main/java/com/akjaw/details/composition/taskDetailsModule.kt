package com.akjaw.details.composition

import com.akjaw.details.domain.calendar.CalendarDaysCalculator
import com.akjaw.details.presentation.calendar.CalendarViewModel
import com.akjaw.details.view.TaskDetailScreenCreator
import com.akjaw.details.view.TaskDetailScreenCreatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskDetailsModule = module {
    factory<TaskDetailScreenCreator> { TaskDetailScreenCreatorImpl() }
    factory { CalendarDaysCalculator() }
    viewModel {
        CalendarViewModel(get(), get())
    }
}
