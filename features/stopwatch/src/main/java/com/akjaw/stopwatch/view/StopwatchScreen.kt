package com.akjaw.stopwatch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.akjaw.core.common.presentation.TimiBottomBar
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.core.common.view.theme.stopwatchBorder
import com.akjaw.core.common.view.theme.taskShape
import com.akjaw.stopwatch.R
import com.akjaw.stopwatch.presentation.StopwatchViewModel
import com.akjaw.task.api.domain.Task
import com.akjaw.task.api.view.tasksPreview
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter

@Composable
internal fun StopwatchScreen(
    navController: NavHostController
) {
    val stopwatchViewModel = hiltViewModel<StopwatchViewModel>()
    val availableTasks = stopwatchViewModel.availableTasks.collectAsState(emptyList())
    val stopwatches = stopwatchViewModel.stopwatches.collectAsState()
    val (isDialogOpen, setIsDialogOpen) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.stopwatch_screen_title)) }) },
        bottomBar = { TimiBottomBar(navController) },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            StopwatchContent(
                stopwatches = stopwatches.value,
                onAddStopwatchClicked = { setIsDialogOpen(true) },
                onStartClicked = { task -> stopwatchViewModel.start(task) },
                onPauseClicked = { task -> stopwatchViewModel.pause(task) },
                onStoppedClicked = { task -> stopwatchViewModel.stop(task) },
            )
            AddStopwatchDialog(
                isDialogOpen = isDialogOpen,
                closeDialog = { setIsDialogOpen(false) },
                availableTasks = availableTasks.value,
                onAddStopwatchClicked = { task -> stopwatchViewModel.start(task) }
            )
        }
    }
}

@Composable
private fun StopwatchContent(
    stopwatches: Map<Task, String>,
    onAddStopwatchClicked: () -> Unit = {},
    onStartClicked: (Task) -> Unit = {},
    onPauseClicked: (Task) -> Unit = {},
    onStoppedClicked: (Task) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp)
            .testTag("StopwatchList"),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stopwatches.forEach { (task, timeString) ->
            item(key = task.id) {
                StopwatchItem(
                    task = task,
                    timeString = timeString,
                    onStartClicked = { onStartClicked(task) },
                    onPauseClicked = { onPauseClicked(task) },
                    onStoppedClicked = { onStoppedClicked(task) },
                )
            }
        }
        item(key = "AddStopwatch") {
            AddNewStopwatchEntryButton(onClick = onAddStopwatchClicked)
        }
    }
}

@Composable
private fun StopwatchItem(
    task: Task,
    timeString: String,
    onStartClicked: () -> Unit = {},
    onPauseClicked: () -> Unit = {},
    onStoppedClicked: () -> Unit = {},
) {
    Card(
        shape = taskShape,
        border = stopwatchBorder(MaterialTheme.colors)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.name,
                        style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                    )
                }
                Box {
                    Text(
                        text = timeString,
                        style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                    )
                }
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                StopwatchButton(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(R.string.stopwatch_screen_start),
                    color = task.backgroundColor,
                    onClick = onStartClicked,
                )
                StopwatchButton(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = stringResource(R.string.stopwatch_screen_pause),
                    color = task.backgroundColor,
                    onClick = onPauseClicked,
                )
                StopwatchButton(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = stringResource(R.string.stopwatch_screen_stop),
                    color = task.backgroundColor,
                    onClick = onStoppedClicked,
                )
            }
        }
    }
}

@Composable
private fun StopwatchButton(
    imageVector: ImageVector,
    contentDescription: String?,
    color: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = color,
            )
        }
    }
}

@Composable
private fun AddNewStopwatchEntryButton(onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(50.dp),
        shape = taskShape,
        border = stopwatchBorder(MaterialTheme.colors)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clickable { onClick() },
        ) {
            Text(text = stringResource(R.string.stopwatch_screen_add_stopwatch))
        }
    }
}

@Preview
@Composable
private fun StopwatchItemPreview() {
    TimiComposeTheme {
        StopwatchItem(
            task = tasksPreview.first(),
            timeString = "00:23:667",
        )
    }
}

@Preview
@Composable
private fun DarkStopwatchItemPreview() {
    TimiComposeTheme(darkTheme = true) {
        StopwatchItem(
            task = tasksPreview.first(),
            timeString = "00:23:667",
        )
    }
}

@Preview
@Composable
private fun AddNewStopwatchEntryButtonPreview() {
    TimiComposeTheme {
        AddNewStopwatchEntryButton()
    }
}

@Preview
@Composable
private fun DarkAddNewStopwatchEntryButtonPreview() {
    TimiComposeTheme(darkTheme = true) {
        AddNewStopwatchEntryButton()
    }
}

@Preview
@Composable
private fun StopwatchContentPreview() {
    TimiComposeTheme {
        StopwatchContent(stopwatches = previewStopwatches)
    }
}

@Preview
@Composable
private fun DarkStopwatchContentPreview() {
    TimiComposeTheme(darkTheme = true) {
        StopwatchContent(stopwatches = previewStopwatches)
    }
}

private val previewStopwatches = tasksPreview
    .map { it to TimestampMillisecondsFormatter.DEFAULT_TIME }.toMap()
