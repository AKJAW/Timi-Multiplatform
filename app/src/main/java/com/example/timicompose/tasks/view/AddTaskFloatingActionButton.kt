package com.example.timicompose.tasks.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.timicompose.tasks.presentation.model.Task

@Composable
fun AddTaskFloatingActionButton(onAddTaskClicked: (Task) -> Unit) {
    val (isAddTaskDialogOpen, setIsAddTaskDialogOpen) = remember { mutableStateOf(false) }

    FloatingActionButton(onClick = { setIsAddTaskDialogOpen(true) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add a task")
    }

    if (isAddTaskDialogOpen) {
        AddTaskDialog(
            closeDialog = { setIsAddTaskDialogOpen(false) },
            onAddTaskClicked = onAddTaskClicked,
        )
    }
}

@Composable
private fun AddTaskDialog(
    closeDialog: () -> Unit,
    onAddTaskClicked: (Task) -> Unit
) {
    val (taskName, setTaskName) = remember { mutableStateOf("") }
    val (isInputError, setIsInputError) = remember { mutableStateOf(false) }
    AddTaskDialog(
        onDismissRequest = closeDialog,
        onAddTaskClicked = {
            if (taskName.isNotBlank()) {
                val task = Task(
                    name = taskName,
                    backgroundColor = Color(132, 212, 240),
                    isSelected = false
                )
                onAddTaskClicked(task)
                closeDialog()
            } else {
                setIsInputError(true)
            }
        },
        taskName = taskName,
        setTaskName = { name ->
            setTaskName(name)
            if (name.isBlank()) {
                setIsInputError(true)
            } else {
                setIsInputError(false)
            }
        },
        isInputError = isInputError
    )
}

@Composable
private fun AddTaskDialog(
    onDismissRequest: () -> Unit,
    onAddTaskClicked: () -> Unit,
    taskName: String,
    setTaskName: (String) -> Unit,
    isInputError: Boolean
) {
    val focusRequester = remember { FocusRequester() } //TODO fix this
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {
                OutlinedTextField(
                    value = taskName,
                    onValueChange = setTaskName,
                    label = { Text(text = "Task name") },
                    isError = isInputError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DialogButton(
                        icon = Icons.Filled.Palette,
                        text = "Color",
                        onClick = { /* Will be implemented in the future */ }
                    )
                    DialogButton(
                        icon = Icons.Filled.Send,
                        text = "Add",
                        onClick = onAddTaskClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    TextButton(onClick = { onClick() }) {
        Text(text = text, style = MaterialTheme.typography.body1)
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colors.primary
        )
    }
}
