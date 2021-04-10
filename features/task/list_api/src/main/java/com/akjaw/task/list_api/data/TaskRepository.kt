package com.akjaw.task.list_api.data

import com.akjaw.task.list_api.domain.Task

interface TaskRepository {

    val tasks: List<Task>
}