package com.akjaw.settings.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.core.common.view.theme.ThemeState
import com.akjaw.core.common.view.theme.TimiComposeTheme

@Composable
fun SettingsScreen() {
    SettingsSection {
        SettingsSwitch(
            icon = Icons.Default.DarkMode,
            title = "Dark mode",
            onClick = { value ->
                ThemeState.isDarkTheme.value = value
            } // TODO move this out to a separate class
        )
    }
}

@Composable
private fun SettingsSection(items: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.padding(all = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(all = 8.dp)
        ) {
            items()
        }
    }
}

@Composable
fun SettingsSwitch(
    icon: ImageVector,
    title: String,
    onClick: (Boolean) -> Unit,
) {
    val (isChecked, setIsChecked) = remember { mutableStateOf(false) }
    val onSwitchValueChange: (Boolean) -> Unit = remember {
        { value ->
            setIsChecked(value)
            onClick(value)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSwitchValueChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier
                .padding(8.dp)
                .size(35.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = { newValue ->
                onSwitchValueChange(newValue)
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    TimiComposeTheme {
        SettingsScreen()
    }
}

@Preview
@Composable
fun DarkSettingsScreenPreview() {
    TimiComposeTheme(darkTheme = true) {
        SettingsScreen()
    }
}
