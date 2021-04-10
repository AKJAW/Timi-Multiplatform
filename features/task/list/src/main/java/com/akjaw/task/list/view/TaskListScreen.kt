package com.akjaw.task.list.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import com.akjaw.core.common.domain.model.Task
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.core.common.view.theme.taskShape
import com.akjaw.core.common.view.theme.taskTextColorFor
import com.akjaw.core.common.view.theme.tasksPreview
import com.akjaw.task.list.presentation.TaskListViewModel

@Composable
fun TaskListScreen(navController: NavHostController) {
    val taskListViewModel = hiltNavGraphViewModel<TaskListViewModel>()
    val tasks = taskListViewModel.tasks.collectAsState()
    Scaffold(
        topBar = { TaskTopAppBar(taskListViewModel = taskListViewModel) },
        floatingActionButton = { AddTaskFloatingActionButton(taskListViewModel::addTask) },
        bottomBar = { com.akjaw.core.common.presentation.TimiBottomBar(navController) },
    ) { paddingValues ->
        TaskList(
            modifier = Modifier.padding(paddingValues),
            tasks = tasks.value,
            onTaskClicked = taskListViewModel::toggleTask
        )
    }

}

@Composable
private fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskClicked: (Task) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(10.dp),
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
        shape = taskShape,
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
        TaskList(Modifier.background(MaterialTheme.colors.background), tasks)
    }
}

@Preview
@Composable
private fun DarkTaskListPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskList(Modifier.background(MaterialTheme.colors.background), tasks)
    }
}

private val tasks = tasksPreview