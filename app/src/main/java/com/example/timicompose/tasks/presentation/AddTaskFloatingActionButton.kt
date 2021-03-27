package com.example.timicompose.tasks.presentation

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.timicompose.tasks.presentation.model.HexColor
import com.example.timicompose.tasks.presentation.model.Task

@Composable
fun AddTaskFloatingActionButton(onAddTaskClicked: (Task) -> Unit) {
    val (isAddTaskDialogOpen, setIsAddTaskDialogOpen) = remember { mutableStateOf(false) }

    FloatingActionButton(onClick = { setIsAddTaskDialogOpen(true) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add a task")
    }

    AddTaskDialog(
        isAddTaskDialogOpen = isAddTaskDialogOpen,
        setIsAddTaskDialogOpen = setIsAddTaskDialogOpen,
        onAddTaskClicked = onAddTaskClicked,
    )
}

@Composable
private fun AddTaskDialog(
    isAddTaskDialogOpen: Boolean,
    setIsAddTaskDialogOpen: (Boolean) -> Unit,
    onAddTaskClicked: (Task) -> Unit
) {
    if (isAddTaskDialogOpen.not()) return
    val (taskName, setTaskName) = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    Dialog(onDismissRequest = { setIsAddTaskDialogOpen(false) }) {
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
                    onValueChange = { text ->
                        setTaskName(text)
                    },
                    label = { Text(text = "Task name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
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
                        onClick = {
                            val task = Task(
                                name = taskName,
                                hexBackgroundColor = HexColor("#80d5ed"),
                                isSelected = false
                            )
                            onAddTaskClicked(task)
                            setIsAddTaskDialogOpen(false)
                        }
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
