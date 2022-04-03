package com.akjaw.timicompose.utils

import com.akjaw.task.TaskEntityQueries

fun TaskEntityQueries.clearDatabase() {
    transaction {
        selectAllTasks().executeAsList().forEach { task ->
            deleteTaskById(task.id)
        }
    }
}
