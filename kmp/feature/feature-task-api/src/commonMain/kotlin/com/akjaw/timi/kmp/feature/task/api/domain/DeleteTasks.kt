package com.akjaw.timi.kmp.feature.task.api.domain

import com.akjaw.timi.kmp.feature.task.api.domain.model.Task

interface DeleteTasks {

    suspend fun execute(tasks: List<Task>)
}
