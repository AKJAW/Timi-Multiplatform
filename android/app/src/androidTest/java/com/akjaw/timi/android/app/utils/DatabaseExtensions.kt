package com.akjaw.timi.android.app.utils

import com.akjaw.timi.kmp.feature.database.TaskEntityQueries

fun TaskEntityQueries.clearDatabase() {
    transaction {
        selectAllTasks().executeAsList().forEach { task ->
            deleteTaskById(task.id)
        }
    }
}
