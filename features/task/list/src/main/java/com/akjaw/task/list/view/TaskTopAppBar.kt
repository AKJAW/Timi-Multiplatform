package com.akjaw.task.list.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akjaw.core.common.view.PaddedAlertDialog
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.task.list.presentation.TaskListViewModel

@Composable
internal fun TaskTopAppBar(taskListViewModel: TaskListViewModel) {
    val (isDeleteDialogOpen, setIsDeleteDialogOpen) = remember { mutableStateOf(false) }

    val tasks = taskListViewModel.tasks.collectAsState(emptyList()).value
    val selectedTasks = tasks.filter { it.isSelected }
    val selectedCount = selectedTasks.count()
    val title = when {
        selectedCount > 0 -> "$selectedCount ${pluralTaskText(selectedCount)} selected"
        else -> "Tasks"
    }
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            if (selectedCount > 0) {
                IconButton(
                    onClick = { setIsDeleteDialogOpen(true) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                    )
                }
            }
        }
    )

    ConfirmDeletionDialog(
        isDeleteDialogOpen = isDeleteDialogOpen,
        setIsDeleteDialogOpen = setIsDeleteDialogOpen,
        selectedCount = selectedCount,
        onDeleteConfirm = {
            taskListViewModel.deleteTasks(selectedTasks)
        },
    )
}

@Composable
private fun ConfirmDeletionDialog(
    isDeleteDialogOpen: Boolean,
    setIsDeleteDialogOpen: (Boolean) -> Unit,
    selectedCount: Int,
    onDeleteConfirm: () -> Unit
) {
    if (isDeleteDialogOpen.not()) return
    PaddedAlertDialog(
        onDismissRequest = {
            setIsDeleteDialogOpen(false)
        },
        title = {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Are you sure you want delete $selectedCount ${pluralTaskText(selectedCount)}?"
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    setIsDeleteDialogOpen(false)
                    onDeleteConfirm()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    setIsDeleteDialogOpen(false)
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

private fun pluralTaskText(count: Int) = when (count) { // TODO replace with resources
    1 -> "Task"
    else -> "Tasks"
}

@Preview
@Composable
private fun ConfirmDeletionDialogPreview() {
    TimiComposeTheme {
        ConfirmDeletionDialog(true, { }, 2, { })
    }
}
