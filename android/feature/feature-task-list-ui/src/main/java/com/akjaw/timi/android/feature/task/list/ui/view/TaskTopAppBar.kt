package com.akjaw.timi.android.feature.task.list.ui.view

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akjaw.timi.android.core.view.PaddedAlertDialog
import com.akjaw.timi.android.core.view.pluralStringResource
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.feature.task.list.ui.R
import com.akjaw.timi.kmp.feature.task.api.presentation.TaskListViewModel

@Composable
internal fun TaskTopAppBar(taskListViewModel: TaskListViewModel) {
    val (isDeleteDialogOpen, setIsDeleteDialogOpen) = remember { mutableStateOf(false) }

    val tasks = taskListViewModel.tasks.collectAsState().value
    val selectedTasks = tasks.filter { it.isSelected }
    val selectedCount = selectedTasks.count()
    val title = when {
        selectedCount > 0 -> pluralStringResource(
            R.plurals.task_list_top_bar_title_selected,
            selectedCount,
            selectedCount
        )
        else -> stringResource(R.string.task_list_top_bar_title)
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
                        contentDescription = stringResource(
                            R.string.task_list_top_bar_delete_description
                        )
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
        }
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
                text = pluralStringResource(
                    R.plurals.task_list_top_bar_are_you_sure,
                    selectedCount,
                    selectedCount
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    setIsDeleteDialogOpen(false)
                    onDeleteConfirm()
                }
            ) {
                Text(stringResource(R.string.task_list_top_bar_confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    setIsDeleteDialogOpen(false)
                }
            ) {
                Text(stringResource(R.string.task_list_top_bar_cancel))
            }
        }
    )
}

@Preview
@Composable
private fun ConfirmDeletionDialogPreview() {
    TimiComposeTheme {
        ConfirmDeletionDialog(true, { }, 2, { })
    }
}
