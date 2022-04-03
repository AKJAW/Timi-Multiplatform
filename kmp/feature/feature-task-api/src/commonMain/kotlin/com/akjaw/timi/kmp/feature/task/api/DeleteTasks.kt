package com.akjaw.timi.kmp.feature.task.api

import com.akjaw.timi.kmp.feature.task.api.model.Task

interface DeleteTasks {

    suspend fun execute(tasks: List<Task>)
}
