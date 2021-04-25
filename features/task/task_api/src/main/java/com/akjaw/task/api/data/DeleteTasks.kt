package com.akjaw.task.api.data

import com.akjaw.task.api.domain.Task

interface DeleteTasks {

    suspend fun execute(tasks: List<Task>)
}
