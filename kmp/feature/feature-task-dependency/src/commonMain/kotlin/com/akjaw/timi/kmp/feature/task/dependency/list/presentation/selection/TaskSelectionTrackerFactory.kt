package com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection

import com.akjaw.timi.kmp.feature.task.api.model.Task
import kotlinx.coroutines.flow.Flow

// TODO make internal
class TaskSelectionTrackerFactory() {

    fun create(originalTaskFlow: Flow<List<Task>>): TaskSelectionTracker =
        TaskSelectionTracker(originalTaskFlow)
}