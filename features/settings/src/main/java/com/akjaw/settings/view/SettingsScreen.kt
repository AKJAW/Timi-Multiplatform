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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.settings.R
import com.akjaw.settings.domain.BooleanSettingsOption
import com.akjaw.settings.presentation.SettingsViewModel

@Composable
internal fun SettingsScreen() {
    val settingsViewModel = hiltNavGraphViewModel<SettingsViewModel>()
    SettingsScreen(
        booleanOptions = settingsViewModel.booleanOptionsFlow.collectAsState().value,
        onSwitchClicked = settingsViewModel::onSwitchValueChange
    )
}

@Composable
internal fun SettingsScreen(
    booleanOptions: Map<BooleanSettingsOption, Boolean>,
    onSwitchClicked: (BooleanSettingsOption, Boolean) -> Unit
) {
    SettingsSection {
        booleanOptions.forEach { (option, value) ->
            SettingsSwitch(
                icon = Icons.Default.DarkMode,
                title = stringResource(id = R.string.boolean_dark_mode),
                currentValue = value,
                onClick = { newValue ->
                    onSwitchClicked(option, newValue)
                }
            )
        }
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
    currentValue: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(currentValue.not()) },
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
            checked = currentValue,
            onCheckedChange = { newValue ->
                onClick(newValue)
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    TimiComposeTheme {
        SettingsScreen(
            booleanOptions = previewOptions,
            onSwitchClicked = { _, _ -> }
        )
    }
}

@Preview
@Composable
fun DarkSettingsScreenPreview() {
    TimiComposeTheme(darkTheme = true) {
        SettingsScreen(
            booleanOptions = previewOptions,
            onSwitchClicked = { _, _ -> }
        )
    }
}

private val previewOptions = mapOf(BooleanSettingsOption.DARK_MODE to false)
