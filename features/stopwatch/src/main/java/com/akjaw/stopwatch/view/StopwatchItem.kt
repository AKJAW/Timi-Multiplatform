package com.akjaw.stopwatch.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.core.common.view.tasksPreview
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.core.common.view.theme.stopwatchBorder
import com.akjaw.core.common.view.theme.taskShape
import com.akjaw.core.common.view.toComposeColor
import com.akjaw.stopwatch.R
import com.akjaw.timi.kmp.feature.task.domain.model.Task

@Composable
fun StopwatchItem(
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
                    color = task.backgroundColor.toComposeColor(),
                    onClick = onStartClicked,
                )
                StopwatchButton(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = stringResource(R.string.stopwatch_screen_pause),
                    color = task.backgroundColor.toComposeColor(),
                    onClick = onPauseClicked,
                )
                StopwatchButton(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = stringResource(R.string.stopwatch_screen_stop),
                    color = task.backgroundColor.toComposeColor(),
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StopwatchItemPreview() {
    TimiComposeTheme {
        StopwatchItem(
            task = tasksPreview.first(),
            timeString = "00:23:667",
        )
    }
}