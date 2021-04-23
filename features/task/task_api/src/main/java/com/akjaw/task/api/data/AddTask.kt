package com.akjaw.task.api.data

import com.akjaw.task.api.domain.Task

interface AddTask {

    fun execute(task: Task)
}
