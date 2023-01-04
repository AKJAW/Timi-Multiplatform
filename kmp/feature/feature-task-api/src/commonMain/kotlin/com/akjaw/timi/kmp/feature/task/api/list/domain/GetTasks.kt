package com.akjaw.timi.kmp.feature.task.api.list.domain

import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface GetTasks {

    fun execute(): Flow<List<Task>>
}
