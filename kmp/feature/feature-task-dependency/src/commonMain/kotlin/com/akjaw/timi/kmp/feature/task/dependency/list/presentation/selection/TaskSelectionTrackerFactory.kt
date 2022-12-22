package com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

// TODO make internal
class TaskSelectionTrackerFactory {

    fun create(
        viewModelScope: ViewModelScope,
        originalTaskFlow: Flow<List<Task>>,
    ): TaskSelectionTracker =
        TaskSelectionTracker(viewModelScope, originalTaskFlow)
}
