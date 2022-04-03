package com.akjaw.timicompose.utils

import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries

fun TaskEntityQueries.clearDatabase() {
    transaction {
        selectAllTasks().executeAsList().forEach { task ->
            deleteTaskById(task.id)
        }
    }
}
