package com.akjaw.timi.android.feature.task.ui.detail

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.timi.android.core.ui.OutlinedListButton
import com.akjaw.timi.android.feature.task.list.ui.R
import com.akjaw.timi.kmp.core.shared.date.CalendarDay
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.TaskDetailViewModel
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.model.TimeEntryUi
import com.akjaw.timi.kmp.feature.task.api.list.domain.model.TimeEntry
import org.koin.androidx.compose.get
import org.koin.core.parameter.parametersOf

@Composable
fun TaskDetailScreen(taskId: Long, viewModel: TaskDetailViewModel = get { parametersOf(taskId) }) {
    var currentDay by remember { mutableStateOf(CalendarDay(29, 12, 2022)) } // TODO move to VM
    val entries by viewModel.getTimeEntries(currentDay).collectAsState(emptyList())

    TaskDetailScreenContent(
        entries = entries,
        addEntry = remember {
            { milliseconds ->
                viewModel.addTimeEntry(TimestampMilliseconds(milliseconds), currentDay)
            }
        },
        onRemoveClick = viewModel::removeTimeEntry
    )
}

@Composable
private fun TaskDetailScreenContent(
    entries: List<TimeEntryUi>,
    addEntry: (Long) -> Unit,
    onRemoveClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hours: Int, minutes: Int ->
                // TODO move to ViewModel
                val totalMinutes = 60 * hours + minutes
                val totalMilliseconds = totalMinutes * 60 * 1000L
                addEntry(totalMilliseconds)
            },
            0, 0, true
        )
    }
    LazyColumn(
        modifier = Modifier.padding(10.dp, 10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(entries, { it.id }) { entry ->
            TimeEntryItem(
                entry = entry,
                onRemoveClick = remember { { onRemoveClick(entry.id) } }
            )
        }
        item(key = "AddEntry") {
            OutlinedListButton(
                text = stringResource(R.string.task_detail_add_time_entry),
                onClick = timePickerDialog::show,
                modifier = Modifier
            )
        }
    }
}

// TODO add swipe to reveal later
@Composable
private fun TimeEntryItem(entry: TimeEntryUi, onRemoveClick: () -> Unit) {
    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(entry.formattedTime, style = itemFont)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(entry.formattedDate, style = itemFont)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.task_detail_remove_time_entry),
                    modifier = Modifier
                        .clickable(onClick = onRemoveClick)
                        .size(22.dp)
                )
            }
        }
    }
}

private val itemFont
    @Composable get() = MaterialTheme.typography.body1.copy(fontSize = 18.sp)