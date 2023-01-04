package com.akjaw.timi.kmp.feature.task.api.list.domain

import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task

interface DeleteTasks {

    suspend fun execute(tasks: List<Task>)
}
