package com.akjaw.task.api.data

import com.akjaw.task.api.domain.Task

interface TaskRepository {

    val tasks: List<Task>
}