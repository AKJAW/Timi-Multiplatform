package com.akjaw.task.list.presentation.selection

import com.akjaw.task.api.domain.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskSelectionTrackerFactory @Inject constructor() {

    fun create(originalTaskFlow: Flow<List<Task>>): TaskSelectionTracker =
        TaskSelectionTracker(originalTaskFlow)
}
