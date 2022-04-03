package com.akjaw.task.list.presentation.selection

import com.akjaw.timi.kmp.feature.task.domain.model.Task
import kotlinx.coroutines.flow.Flow

class TaskSelectionTrackerFactory() {

    fun create(originalTaskFlow: Flow<List<Task>>): TaskSelectionTracker =
        TaskSelectionTracker(originalTaskFlow)
}