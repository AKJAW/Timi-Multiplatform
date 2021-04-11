package com.akjaw.task.list.data

import androidx.compose.ui.graphics.Color
import com.akjaw.task.list_api.data.TaskRepository
import com.akjaw.task.list_api.domain.Task
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class TaskRepositoryImpl @Inject constructor(): TaskRepository {

    override val tasks: List<Task> = listOf(
        Task("Task 1", Color(132, 212, 240), false),
        Task("Task 2", Color(230, 240, 132), false),
        Task("Task 3", Color(132, 240, 161), false),
    )
}