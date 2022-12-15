package com.akjaw.timi.kmp.feature.task.api.domain

import com.akjaw.timi.kmp.feature.task.api.domain.model.Task

interface AddTask {

    suspend fun execute(task: Task)
}
