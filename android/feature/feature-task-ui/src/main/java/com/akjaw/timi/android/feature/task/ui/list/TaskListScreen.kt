package com.akjaw.timi.android.feature.task.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akjaw.timi.android.core.presentation.TaskDestinations
import com.akjaw.timi.android.core.presentation.TimiBottomBar
import com.akjaw.timi.android.core.view.tasksPreview
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.core.view.theme.taskShape
import com.akjaw.timi.android.core.view.theme.taskTextColorFor
import com.akjaw.timi.android.core.view.toComposeColor
import com.akjaw.timi.android.feature.task.ui.R
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.list.presentation.TaskListViewModel
import org.koin.androidx.compose.get

@Composable
fun TaskListScreen(
    navController: NavHostController,
    taskListViewModel: TaskListViewModel = get()
) {
    val tasks = taskListViewModel.tasks.collectAsState(emptyList())
    Scaffold(
        topBar = { TaskTopAppBar(taskListViewModel = taskListViewModel) },
        floatingActionButton = { AddTaskFloatingActionButton(taskListViewModel::addTask) },
        bottomBar = { TimiBottomBar(navController) }
    ) { paddingValues ->
        TaskList(
            modifier = Modifier.padding(paddingValues),
            tasks = tasks.value,
            onTaskClick = { task ->
                val route = TaskDestinations.Details.destination(task.id)
                navController.navigate(route) {
                    launchSingleTop = true
                }
            },
            onTaskLongClick = taskListViewModel::toggleTask
        )
    }
}

@Composable
private fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit = { },
    onTaskLongClick: (Task) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .testTag("TaskList"),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = tasks, key = { it.id }) {
            TaskItem(
                task = it,
                onTaskClicked = onTaskClick,
                onTaskLongClick = onTaskLongClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskItem(
    task: Task,
    onTaskClicked: (Task) -> Unit,
    onTaskLongClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = taskShape,
        backgroundColor = task.backgroundColor.toComposeColor(),
        elevation = 0.dp
    ) {
        val buttonWidth = remember { 50.dp }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onTaskClicked(task) },
                    onLongClick = { onTaskLongClick(task) }
                )
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = buttonWidth),
                text = task.name,
                style = MaterialTheme.typography.h6.copy(
                    color = taskTextColorFor(task.backgroundColor.toComposeColor())
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TaskSelectButton(task.isSelected, buttonWidth)
            }
        }
    }
}

@Composable
private fun TaskSelectButton(isSelected: Boolean, buttonWidth: Dp) {
    val transition = updateTransition(targetState = isSelected)
    val backgroundWidth: Dp by transition.animateDp { if (it) 0.dp else buttonWidth }
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
        contentDescription = stringResource(R.string.task_list_screen_checkmark_description),
        tint = LocalContentColor.current.copy(iconAlpha)
    )
}

@Preview
@Composable
private fun TaskItemPreview() {
    TimiComposeTheme {
        TaskItem(tasks.first().copy(isSelected = false), {}, {})
    }
}

@Preview
@Composable
private fun TaskItemSelectedPreview() {
    TimiComposeTheme {
        TaskItem(tasks.first().copy(isSelected = true), {}, {})
    }
}

@Preview
@Composable
private fun DarkTaskItemPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskItem(tasks.first().copy(isSelected = false), {}, {})
    }
}

@Preview
@Composable
private fun DarkTaskItemSelectedPreview() {
    TimiComposeTheme(darkTheme = true) {
        TaskItem(tasks.first().copy(isSelected = true), {}, {})
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
