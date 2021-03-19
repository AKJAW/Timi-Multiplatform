package com.example.timicompose.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timicompose.tasks.data.TaskRepository
import com.example.timicompose.tasks.presentation.TaskViewModel
import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.ui.theme.TimiComposeTheme

@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    val tasks = taskViewModel.tasks.collectAsState()
    TaskList(tasks = tasks.value)
}

@Composable
fun TaskList(tasks: List<Task>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(10.dp, 10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tasks.forEach { task ->
            item {
                TaskItem(task)
            }
        }
    }
}

@Composable
private fun TaskItem(task: Task, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { /* Empty */ },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.Green
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = task.name)
        }
    }
}

@Preview
@Composable
fun TaskListPreview() {
    TimiComposeTheme {
        TaskList(TaskRepository.tasks, Modifier.background(Color.White))
    }
}
