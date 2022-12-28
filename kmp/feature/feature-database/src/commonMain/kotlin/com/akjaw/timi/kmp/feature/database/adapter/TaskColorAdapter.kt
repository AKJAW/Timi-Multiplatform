package com.akjaw.timi.kmp.feature.database.adapter

import com.akjaw.timi.kmp.feature.database.TaskEntity
import com.akjaw.timi.kmp.feature.task.api.domain.model.TaskColor
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

internal val taskColorAdapter = TaskEntity.Adapter(TaskColorAdapter())
