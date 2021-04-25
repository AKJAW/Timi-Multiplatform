package com.akjaw.task.api.data

import com.akjaw.task.api.domain.Task
import kotlinx.coroutines.flow.Flow

interface GetTasks {

    fun execute(): Flow<List<Task>>
}
