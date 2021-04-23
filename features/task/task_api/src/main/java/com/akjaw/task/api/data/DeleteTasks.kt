package com.akjaw.task.api.data

import com.akjaw.task.api.domain.Task

interface DeleteTasks {

    fun execute(tasks: List<Task>)
}
