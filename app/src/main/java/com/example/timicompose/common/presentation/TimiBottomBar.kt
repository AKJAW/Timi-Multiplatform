package com.example.timicompose.common.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.timicompose.ui.theme.TimiComposeTheme

enum class BottomBarScreen(
    val imageVector: ImageVector,
    val text: String,
) {
    HOME(
        imageVector = Icons.Filled.Home,
        text = "Tasks",
    ),
    TIMER(
        imageVector = Icons.Filled.Timer,
        text = "Timer",
    ),
    SETTINGS(
        imageVector = Icons.Filled.Settings,
        text = "Settings",
    );
}

@Composable
fun TimiBottomBar() {
    BottomNavigation {
        BottomBarScreen.values().forEach { screen ->
            BottomNavigationItem(
                selected = screen == BottomBarScreen.HOME,
                onClick = { /*TODO*/ },
                icon = {
                    BottomNavIcon(
                        imageVector = screen.imageVector,
                        text = screen.text
                    )
                },
            )
        }
    }
}

@Composable
private fun BottomNavIcon(
    imageVector: ImageVector,
    text: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = imageVector, contentDescription = text)
        Text(text = text)
    }
}

@Preview
@Composable
fun TimiBottomBarPreview() {
    TimiComposeTheme {
        TimiBottomBar()
    }
}

@Preview
@Composable
fun TimiBottomBarPreviewDark() {
    TimiComposeTheme(darkTheme = true) {
        TimiBottomBar()
    }
}
