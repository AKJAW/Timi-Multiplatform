package com.example.timicompose.tasks.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.timicompose.tasks.presentation.TaskListViewModel
import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.ui.theme.TimiComposeTheme
import com.example.timicompose.ui.theme.taskTextColorFor

@Composable
fun TaskScreen(taskListViewModel: TaskListViewModel) {
    val tasks = taskListViewModel.tasks.collectAsState()
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = { TaskTopAppBar(taskListViewModel = taskListViewModel) },
        floatingActionButton = { AddTaskFloatingActionButton(taskListViewModel::addTask) },
        content = {
            TaskList(tasks = tasks.value, onTaskClicked = taskListViewModel::toggleTask)
        }
    )

}

@Composable
fun TaskList(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onTaskClicked: (Task) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(10.dp, 10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tasks.forEach { task ->
            item {
                TaskItem(task, onTaskClicked)
            }
        }
    }
}

@Composable
private fun TaskItem(task: Task, onTaskClicked: (Task) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onTaskClicked(task) },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = task.backgroundColor,
        elevation = 0.dp,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.h6.copy(
                    color = taskTextColorFor(task.backgroundColor)
                )
            )
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TaskSelectButton(task.isSelected)
            }
        }
    }
}

@Composable
private fun TaskSelectButton(isSelected: Boolean) {
    val transition = updateTransition(targetState = isSelected)
    val backgroundWidth: Dp by transition.animateDp { if (it) 0.dp else 50.dp }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .width(backgroundWidth)
            .fillMaxHeight()
            .animateContentSize(animationSpec = spring(stiffness = Spring.StiffnessHigh))
    )
    val iconAlpha: Float by transition.animateFloat { if (it) 1f else 0.1f }
    Icon(
        modifier = Modifier.padding(end = 15.dp),
        imageVector = Icons.Filled.Done,
        contentDescription = "checkmark",
        tint = LocalContentColor.current.copy(iconAlpha)
    )
}

@Preview
@Composable
private fun TaskItemPreview() {
    TimiComposeTheme {
        TaskItem(tasks.first().copy(isSelected = false), {})
    }
}

@Preview
@Composable
private fun TaskItemSelectedPreview() {
    TimiComposeTheme {
        TaskItem(tasks.first().copy(isSelected = true), {})
    }
}

@Preview
@Composable
private fun DarkTaskItemPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskItem(tasks.first().copy(isSelected = false), {})
    }
}

@Preview
@Composable
private fun DarkTaskItemSelectedPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskItem(tasks.first().copy(isSelected = true), {})
    }
}

@Preview
@Composable
private fun TaskListPreview() {
    TimiComposeTheme {
        TaskList(tasks, Modifier.background(MaterialTheme.colors.background))
    }
}

@Preview
@Composable
private fun DarkTaskListPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskList(tasks, Modifier.background(MaterialTheme.colors.background))
    }
}

private val tasks = listOf(
    Task("Task 1", Color(132, 212, 240), false),
    Task("Task 2", Color(230, 240, 132), false),
    Task("Task 3", Color(132, 240, 161), false),
)