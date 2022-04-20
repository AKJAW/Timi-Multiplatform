package com.akjaw.timi.android.app.utils

import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries

fun TaskEntityQueries.clearDatabase() {
    transaction {
        selectAllTasks().executeAsList().forEach { task ->
            deleteTaskById(task.id)
        }
    }
}
