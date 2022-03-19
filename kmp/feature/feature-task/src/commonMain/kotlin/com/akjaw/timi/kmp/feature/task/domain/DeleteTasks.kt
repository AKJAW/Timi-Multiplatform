package com.akjaw.timi.kmp.feature.task.domain

import com.akjaw.timi.kmp.feature.task.domain.model.Task

interface DeleteTasks {

    suspend fun execute(tasks: List<Task>)
}
