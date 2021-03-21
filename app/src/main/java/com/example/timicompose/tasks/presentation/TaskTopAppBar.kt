package com.example.timicompose.tasks.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun TaskTopAppBar(taskViewModel: TaskViewModel) {
    val tasks = taskViewModel.tasks.collectAsState().value
    val selectedTasks = tasks.count { it.isSelected }
    val title = when {
        selectedTasks == 1 -> "$selectedTasks Task selected"
        selectedTasks > 1 -> "$selectedTasks Tasks selected"
        else -> "Tasks"
    }
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            if (selectedTasks > 0) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.clickable {  },
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                    )
                }
            }
        }
    )
}
