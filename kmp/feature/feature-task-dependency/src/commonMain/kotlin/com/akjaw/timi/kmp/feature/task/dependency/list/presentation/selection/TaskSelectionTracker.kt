package com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.rickclephas.kmm.viewmodel.ViewModelScope
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class TaskSelectionTracker(
    viewModelScope: ViewModelScope,
    originalTaskFlow: Flow<List<Task>>,
) {

    private val selections = MutableStateFlow<List<Long>>(emptyList())
    val tasksWithSelection: StateFlow<List<Task>> = combine(
        originalTaskFlow,
        selections,
        ::markSelectedTasks
    ).stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun toggleTask(task: Task) {
        if (task.isSelected) {
            selections.value = selections.value.filterNot { it == task.id }
        } else {
            selections.value = selections.value + task.id
        }
    }

    private fun markSelectedTasks(tasks: List<Task>, ids: List<Long>): List<Task> {
        return tasks.map { task ->
            task.copy(isSelected = ids.contains(task.id))
        }
    }
}
