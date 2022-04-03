package com.akjaw.timi.kmp.feature.task.api

import com.akjaw.timi.kmp.feature.task.api.model.Task
import kotlinx.coroutines.flow.Flow

interface GetTasks {

    fun execute(): Flow<List<Task>>
}
