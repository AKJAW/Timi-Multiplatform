package com.akjaw.stopwatch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.core.common.domain.model.Task
import com.akjaw.core.common.view.PaddedDialog
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.core.common.view.theme.taskShape
import com.akjaw.core.common.view.theme.taskTextColorFor
import com.akjaw.core.common.view.theme.tasksPreview

@Composable
internal fun AddStopwatchDialog(
    isDialogOpen: Boolean,
    closeDialog: () -> Unit,
    availableTasks: List<Task>,
    onAddStopwatchClicked: (Task) -> Unit
) {
    if (isDialogOpen) {
        PaddedDialog(onDismissRequest = closeDialog) { modifier ->
            AddStopwatchDialogContent(
                modifier = modifier,
                availableTasks = availableTasks,
                onAddStopwatchClicked = onAddStopwatchClicked,
                closeDialog = closeDialog,
            )
        }
    }
}

@Composable
private fun AddStopwatchDialogContent(
    availableTasks: List<Task>,
    modifier: Modifier = Modifier,
    onAddStopwatchClicked: (Task) -> Unit = {},
    closeDialog: () -> Unit = {},
) {
    Card(modifier = modifier.then(Modifier.heightIn(max = 300.dp))) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 20.sp,
                        ),
                        text = "Choose a task"
                    )
                }
            }
            availableTasks.forEach { task ->
                item {
                    AddStopwatchTaskItem(
                        task = task,
                        onAddStopwatchClicked = onAddStopwatchClicked,
                        closeDialog = closeDialog
                    )
                }
            }
        }
    }
}

@Composable
private fun AddStopwatchTaskItem(
    task: Task,
    onAddStopwatchClicked: (Task) -> Unit = {},
    closeDialog: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .clickable {
                onAddStopwatchClicked(task)
                closeDialog()
            },
        elevation = 0.dp,
        backgroundColor = task.backgroundColor,
        shape = taskShape,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = MaterialTheme.typography.body1.copy(
                    color = taskTextColorFor(task.backgroundColor),
                    fontSize = 22.sp,
                ),
                text = task.name
            )
        }
    }
}

@Preview
@Composable
private fun AddStopwatchTaskItemPreview() {
    TimiComposeTheme {
        AddStopwatchTaskItem(tasksPreview.first())
    }
}

@Preview
@Composable
private fun AddStopwatchDialogContentPreview() {
    TimiComposeTheme {
        AddStopwatchDialogContent(tasksPreview + tasksPreview)
    }
}

@Preview
@Composable
private fun DarkAddStopwatchDialogContentPreview() {
    TimiComposeTheme(darkTheme = true) {
        AddStopwatchDialogContent(tasksPreview + tasksPreview)
    }
}
