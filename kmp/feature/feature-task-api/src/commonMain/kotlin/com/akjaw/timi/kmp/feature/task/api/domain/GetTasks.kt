package com.akjaw.timi.kmp.feature.task.api.domain

import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface GetTasks {

    fun execute(): Flow<List<Task>>
}
