package com.akjaw.timi.android.stopwatch.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akjaw.core.common.presentation.TimiBottomBar
import com.akjaw.core.common.view.tasksPreview
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.timi.kmp.feature.stopwatch.presentation.StopwatchViewModel
import com.akjaw.timi.kmp.feature.task.api.model.Task
import org.koin.androidx.compose.get

@Composable
fun StopwatchScreen(
    navController: NavHostController,
    viewModel: StopwatchViewModel = get(),
) {
    val availableTasks = viewModel.availableTasks.collectAsState(emptyList())
    val stopwatches = viewModel.stopwatches.collectAsState()
    val (isDialogOpen, setIsDialogOpen) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.stopwatch_screen_title)) }) },
        bottomBar = { TimiBottomBar(navController) },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            StopwatchContent(
                stopwatches = stopwatches.value,
                onAddStopwatchClicked = { setIsDialogOpen(true) },
                onStartClicked = { task -> viewModel.start(task) },
                onPauseClicked = { task -> viewModel.pause(task) },
                onStoppedClicked = { task -> viewModel.stop(task) },
            )
            AddStopwatchDialog(
                isDialogOpen = isDialogOpen,
                closeDialog = { setIsDialogOpen(false) },
                availableTasks = availableTasks.value,
                onAddStopwatchClicked = { task -> viewModel.start(task) }
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StopwatchContentPreview() {
    TimiComposeTheme {
        StopwatchContent(stopwatches = previewStopwatches)
    }
}

private val previewStopwatches = tasksPreview.associateWith { "00:00:000" }
