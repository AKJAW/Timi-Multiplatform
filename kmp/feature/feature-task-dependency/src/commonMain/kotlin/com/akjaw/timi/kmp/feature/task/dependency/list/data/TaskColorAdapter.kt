package com.akjaw.timi.kmp.feature.task.dependency.list.data

import com.akjaw.timi.kmp.feature.task.api.model.TaskColor
import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntity
import com.squareup.sqldelight.ColumnAdapter

private class TaskColorAdapter : ColumnAdapter<TaskColor, String> {

    // TODO safeguard?
    override fun decode(databaseValue: String): TaskColor {
        val (red, green, blue) = databaseValue.split(",").map { it.toFloat() }
        return TaskColor(red, green, blue)
    }

    override fun encode(value: TaskColor): String {
        return listOf(value.red, value.green, value.blue).joinToString()
    }
}

val taskColorAdapter = TaskEntity.Adapter(TaskColorAdapter())