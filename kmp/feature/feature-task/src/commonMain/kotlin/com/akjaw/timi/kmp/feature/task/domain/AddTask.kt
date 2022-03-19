package com.akjaw.timi.kmp.feature.task.domain

import com.akjaw.timi.kmp.feature.task.domain.model.Task

interface AddTask {

    suspend fun execute(task: Task)
}
