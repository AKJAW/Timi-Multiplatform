package com.example.timicompose.tasks.presentation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.timicompose.tasks.presentation.model.HexColor
import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.tasks.presentation.model.toColor
import com.example.timicompose.ui.theme.TimiComposeTheme

@Composable
fun TaskScreen(taskListViewModel: TaskListViewModel) {
    val tasks = taskListViewModel.tasks.collectAsState()
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = { TaskTopAppBar(taskListViewModel = taskListViewModel) },
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
    val color = task.hexBackgroundColor.toColor()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onTaskClicked(task) },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = color,
        elevation = 0.dp,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(text = task.name)
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
fun TaskItemPreview() {
    TimiComposeTheme {
        TaskItem(tasks.first().copy(isSelected = false), {})
    }
}

@Preview
@Composable
fun TaskItemSelectedPreview() {
    TimiComposeTheme {
        TaskItem(tasks.first().copy(isSelected = true), {})
    }
}

@Preview
@Composable
fun DarkTaskItemPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskItem(tasks.first().copy(isSelected = false), {})
    }
}

@Preview
@Composable
fun DarkTaskItemSelectedPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskItem(tasks.first().copy(isSelected = true), {})
    }
}

@Preview
@Composable
fun TaskListPreview() {
    TimiComposeTheme {
        TaskList(tasks, Modifier.background(MaterialTheme.colors.background))
    }
}

@Preview
@Composable
fun DarkTaskListPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskList(tasks, Modifier.background(MaterialTheme.colors.background))
    }
}

private val tasks = listOf(
    Task("Task 1", HexColor("#80d5ed"), false),
    Task("Task 2", HexColor("#e5f087"), false),
    Task("Task 3", HexColor("#80f0a3"), false),
)