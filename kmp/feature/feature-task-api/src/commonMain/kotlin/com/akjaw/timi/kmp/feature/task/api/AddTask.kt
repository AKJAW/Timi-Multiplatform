package com.akjaw.timi.kmp.feature.task.api

import com.akjaw.timi.kmp.feature.task.api.model.Task

interface AddTask {

    suspend fun execute(task: Task)
}
