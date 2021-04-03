package com.example.timicompose.stopwatch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.timicompose.common.presentation.TimiBottomBar
import com.example.timicompose.stopwatch.domain.StopwatchOrchestator
import com.example.timicompose.stopwatch.presentation.StopwatchEntry
import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.ui.theme.TimiComposeTheme
import com.example.timicompose.ui.theme.stopwatchBorder
import com.example.timicompose.ui.theme.taskShape
import com.example.timicompose.ui.theme.tasksPreview

@Composable
fun StopwatchScreen(navController: NavHostController, stopwatchOrchestator: StopwatchOrchestator) {
    val stopwatches = stopwatchOrchestator.ticker.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Stopwatch") }) },
        bottomBar = { TimiBottomBar(navController) },
    ) {
        StopwatchContent(
            stopwatches = stopwatches.value,
            onStartClicked = { task -> stopwatchOrchestator.start(task) },
            onPauseClicked = { task -> stopwatchOrchestator.pause(task) },
        )
    }
}

@Composable
fun StopwatchContent(
    stopwatches: Map<Task, String>,
    onStartClicked: (Task) -> Unit,
    onPauseClicked: (Task) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 10.dp),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stopwatches.keys.forEach { task ->
                item {
                    val stopwatchEntry = StopwatchEntry(task, stopwatches[task]!!)
                    StopwatchItem(
                        stopwatchEntry = stopwatchEntry,
                        onStartClicked = { onStartClicked(task) },
                        onPauseClicked = { onPauseClicked(task) }
                    )
                }
            }
        }
        AddNewStopwatchEntryButton()
    }
}

@Composable
fun StopwatchItem(
    stopwatchEntry: StopwatchEntry,
    onStartClicked: () -> Unit,
    onPauseClicked: () -> Unit,
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
                Text(
                    text = stopwatchEntry.task.name,
                    style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                )
                Text(
                    text = stopwatchEntry.timeString,
                    style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                )
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                StopwatchButton(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Start",
                    color = stopwatchEntry.task.backgroundColor,
                    onClick = onStartClicked,
                )
                StopwatchButton(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Pause",
                    color = stopwatchEntry.task.backgroundColor,
                    onClick = onPauseClicked,
                )
                StopwatchButton(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = "Stop",
                    color = stopwatchEntry.task.backgroundColor,
                    onClick = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
fun StopwatchButton(
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
fun AddNewStopwatchEntryButton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(50.dp)
            .clickable { /* TODO */ },
        shape = taskShape,
        border = stopwatchBorder(MaterialTheme.colors)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Add a new stopwatch")
        }
    }
}

@Preview
@Composable
fun StopwatchItemPreview() {
    TimiComposeTheme {
        StopwatchItem(
            stopwatchEntry = StopwatchEntry(tasksPreview.first(), timeString = "23:66"),
            onStartClicked = {},
            onPauseClicked = {}
        )
    }
}

@Preview
@Composable
fun DarkStopwatchItemPreview() {
    TimiComposeTheme(darkTheme = true) {
        StopwatchItem(
            stopwatchEntry = StopwatchEntry(tasksPreview.first(), timeString = "23:66"),
            onStartClicked = {},
            onPauseClicked = {}
        )
    }
}

@Preview
@Composable
fun AddNewStopwatchEntryButtonPreview() {
    TimiComposeTheme {
        AddNewStopwatchEntryButton()
    }
}

@Preview
@Composable
fun DarkAddNewStopwatchEntryButtonPreview() {
    TimiComposeTheme(darkTheme = true) {
        AddNewStopwatchEntryButton()
    }
}

@Preview
@Composable
fun StopwatchContentPreview() {
    TimiComposeTheme {
        StopwatchContent(
            stopwatches = previewStopwatches,
            onStartClicked = {},
            onPauseClicked = {}
        )
    }
}

@Preview
@Composable
fun DarkStopwatchContentPreview() {
    TimiComposeTheme(darkTheme = true) {
        StopwatchContent(
            stopwatches = previewStopwatches,
            onStartClicked = {},
            onPauseClicked = {}
        )

    }
}

private val previewStopwatches = tasksPreview.map { it to "00:00" }.toMap()