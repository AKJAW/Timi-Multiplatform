package com.example.timicompose.tasks.view

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.ui.theme.TimiComposeTheme
import com.example.timicompose.ui.theme.taskColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    onAddTaskClicked: (Task) -> Unit,
    defaultBackgroundColor: Color = MaterialTheme.colors.background,
) {
    val composableScope = rememberCoroutineScope()
    val (taskName, setTaskName) = remember { mutableStateOf("") }
    val (isInputError, setIsInputError) = remember { mutableStateOf(false) }
    val (isColorPickerShown, setIsColorPickerShown) = remember { mutableStateOf(false) }
    val (taskColor) = remember { mutableStateOf(Animatable(defaultBackgroundColor)) }
    AddTaskDialog(
        onDismissRequest = closeDialog,
        onShowColorClicked = { setIsColorPickerShown(isColorPickerShown.not()) },
        onAddTaskClicked = {
            if (taskName.isNotBlank()) {
                val task = Task(
                    name = taskName,
                    backgroundColor = taskColor.value,
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
        taskColor = taskColor.value,
        isInputError = isInputError,
        colorPicker = {
            ColorPicker(
                composableScope = composableScope,
                isColorPickerShown = isColorPickerShown,
                taskColor = taskColor
            )
        }
    )
}

@Composable
private fun AddTaskDialog(
    onDismissRequest: () -> Unit,
    onShowColorClicked: () -> Unit,
    onAddTaskClicked: () -> Unit,
    taskName: String,
    setTaskName: (String) -> Unit,
    taskColor: Color,
    isInputError: Boolean,
    colorPicker: @Composable () -> Unit,
) {
    val focusRequester = remember { FocusRequester() } //TODO fix this
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            backgroundColor = taskColor
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
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
                        onClick = onShowColorClicked
                    )
                    DialogButton(
                        icon = Icons.Filled.Send,
                        text = "Add",
                        onClick = onAddTaskClicked
                    )
                }
                colorPicker()
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

@Composable
private fun ColorPicker(
    composableScope: CoroutineScope,
    isColorPickerShown: Boolean,
    taskColor: Animatable<Color, AnimationVector4D>
) {
    if (isColorPickerShown) {
        ColorPicker(
            colors = taskColors,
            onColorClicked =
            { targetColor ->
                composableScope.launch {
                    taskColor.animateTo(targetColor)
                }
            }
        )
    }
}

@Composable
private fun ColorPicker(colors: List<Color>, onColorClicked: (Color) -> Unit) {
    LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
        colors.forEach { color ->
            item {
                Box(
                    modifier = Modifier
                        .clickable { onColorClicked(color) }
                        .background(color)
                        .border(width = 1.dp, color = MaterialTheme.colors.background)
                        .size(50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    TimiComposeTheme {
        ColorPicker(colors = taskColors, onColorClicked = {})
    }
}

@Preview
@Composable
fun DarkColorPickerPreview() {
    TimiComposeTheme(darkTheme = true) {
        ColorPicker(colors = taskColors, onColorClicked = {})
    }
}
