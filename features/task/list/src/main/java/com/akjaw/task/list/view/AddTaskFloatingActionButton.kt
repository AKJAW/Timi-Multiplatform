package com.akjaw.task.list.view

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.core.common.view.theme.taskColors
import com.akjaw.core.common.view.theme.taskTextColorFor
import com.akjaw.task.api.domain.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun AddTaskFloatingActionButton(onAddTaskClicked: (Task) -> Unit) {
    val (isAddTaskDialogOpen, setIsAddTaskDialogOpen) = remember { mutableStateOf(false) }

    FloatingActionButton(onClick = { setIsAddTaskDialogOpen(true) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add a task")
    }

    if (isAddTaskDialogOpen) {
        AddTaskDialog(
            isAddTaskDialogOpen = isAddTaskDialogOpen,
            closeDialog = { setIsAddTaskDialogOpen(false) },
            onAddTaskClicked = onAddTaskClicked,
        )
    }
}

@Composable
private fun AddTaskDialog(
    isAddTaskDialogOpen: Boolean,
    closeDialog: () -> Unit,
    onAddTaskClicked: (Task) -> Unit
) {
    if (isAddTaskDialogOpen) {
        Dialog(onDismissRequest = closeDialog) {
            AddTaskDialogContent(
                closeDialog = closeDialog,
                onAddTaskClicked = onAddTaskClicked,
            )
        }
    }
}

@Composable
private fun AddTaskDialogContent(
    closeDialog: () -> Unit,
    onAddTaskClicked: (Task) -> Unit,
    defaultBackgroundColor: Color = MaterialTheme.colors.background,
    defaultTextColor: Color = contentColorFor(defaultBackgroundColor),
) {
    val composableScope = rememberCoroutineScope()
    val (taskName, setTaskName) = remember { mutableStateOf("") }
    val (isInputError, setIsInputError) = remember { mutableStateOf(false) }
    val (isColorPickerShown, setIsColorPickerShown) = remember { mutableStateOf(false) }
    val (taskColor) = remember { mutableStateOf(Animatable(defaultBackgroundColor)) }
    val (textColor) = remember { mutableStateOf(Animatable(defaultTextColor)) }
    AddTaskDialogContent(
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
        isInputError = isInputError,
        composableScope = composableScope,
        isColorPickerShown = isColorPickerShown,
        taskColor = taskColor,
        textColor = textColor,
    )
}

@Composable
private fun AddTaskDialogContent(
    onShowColorClicked: () -> Unit,
    onAddTaskClicked: () -> Unit,
    taskName: String,
    setTaskName: (String) -> Unit,
    isInputError: Boolean,
    composableScope: CoroutineScope,
    isColorPickerShown: Boolean,
    taskColor: Animatable<Color, AnimationVector4D>,
    textColor: Animatable<Color, AnimationVector4D>,
) {
    val focusRequester = remember { FocusRequester() } // TODO fix this
    Card(
        backgroundColor = taskColor.value
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = textColor.value,
                    focusedBorderColor = textColor.value,
                    focusedLabelColor = textColor.value,
                    unfocusedBorderColor = textColor.value.copy(ContentAlpha.medium),
                    unfocusedLabelColor = textColor.value.copy(ContentAlpha.medium),
                    placeholderColor = textColor.value.copy(ContentAlpha.medium),
                ),
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
                    textColor = textColor.value,
                    onClick = onShowColorClicked
                )
                DialogButton(
                    icon = Icons.Filled.Send,
                    text = "Add",
                    textColor = textColor.value,
                    onClick = onAddTaskClicked
                )
            }
            ColorPicker(
                composableScope = composableScope,
                isColorPickerShown = isColorPickerShown,
                taskColor = taskColor,
                textColor = textColor,
            )
        }
    }
}

@Composable
private fun DialogButton(icon: ImageVector, text: String, textColor: Color, onClick: () -> Unit) {
    TextButton(onClick = { onClick() }) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(color = textColor, fontSize = 18.sp)
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = textColor
        )
    }
}

@Composable
private fun ColorPicker(
    composableScope: CoroutineScope,
    isColorPickerShown: Boolean,
    taskColor: Animatable<Color, AnimationVector4D>,
    textColor: Animatable<Color, AnimationVector4D>,
) {
    if (isColorPickerShown) {
        ColorPicker(
            colors = taskColors,
            onColorClicked =
            { targetColor ->
                // Two parallel animations don't work together so well
                composableScope.launch {
                    taskColor.animateTo(targetColor)
                }
                composableScope.launch {
                    textColor.animateTo(taskTextColorFor(targetColor))
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
private fun ColorPickerPreview() {
    TimiComposeTheme {
        ColorPicker(colors = taskColors, onColorClicked = {})
    }
}

@Preview
@Composable
private fun DarkColorPickerPreview() {
    TimiComposeTheme(darkTheme = true) {
        ColorPicker(colors = taskColors, onColorClicked = {})
    }
}

@Preview
@Composable
private fun AddTaskDialogPreview() {
    TimiComposeTheme {
        AddTaskDialogContent(closeDialog = {}, onAddTaskClicked = {})
    }
}

@Preview
@Composable
private fun DarkAddTaskDialogPreview() {
    TimiComposeTheme(darkTheme = true) {
        AddTaskDialogContent(closeDialog = {}, onAddTaskClicked = {})
    }
}

@Preview
@Composable
private fun AddTaskDialogWithColorPickerPreview() {
    TimiComposeTheme {
        TestAddTaskDialogWithColorPicker()
    }
}

@Preview
@Composable
private fun DarkAddTaskDialogWithColorPickerPreview() {
    TimiComposeTheme(darkTheme = true) {
        TestAddTaskDialogWithColorPicker()
    }
}

@Preview
@Composable
private fun SelectedAddTaskDialogWithColorPickerPreview() {
    TimiComposeTheme {
        TestAddTaskDialogWithColorPicker(Color(132, 212, 240))
    }
}

@Preview
@Composable
private fun SelectedDarkAddTaskDialogWithColorPickerPreview() {
    TimiComposeTheme(darkTheme = true) {
        TestAddTaskDialogWithColorPicker(Color(132, 212, 240))
    }
}

@Composable
private fun TestAddTaskDialogWithColorPicker(
    backgroundColor: Color = MaterialTheme.colors.background,
    defaultTextColor: Color = contentColorFor(backgroundColor),
) {
    val taskColor = remember { mutableStateOf(Animatable(backgroundColor)) }
    val textColor = remember { mutableStateOf(Animatable(defaultTextColor)) }
    AddTaskDialogContent(
        onShowColorClicked = {},
        onAddTaskClicked = {},
        taskName = "",
        setTaskName = {},
        isInputError = false,
        composableScope = rememberCoroutineScope(),
        isColorPickerShown = true,
        taskColor = taskColor.value,
        textColor = textColor.value,
    )
}
