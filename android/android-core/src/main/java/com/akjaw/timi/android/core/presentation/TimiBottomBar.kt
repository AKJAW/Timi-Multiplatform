package com.akjaw.timi.android.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme

private val values = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Stopwatch
    // BottomBarScreen.Settings
)

object TaskDestinations {
    object List {
        val route = "task_list"
    }

    object Details {
        val route = "task_details/{taskId}"

        fun destination(taskId: Long): String {
            return "task_details/$taskId"
        }
    }
}

object StopwatchDestinations {
    object List {
        val route = "stopwatch_list"
    }
}

object SettingsDestinations {
    object Home {
        val route = "settings"
    }
}

sealed class BottomBarScreen(
    val imageVector: ImageVector,
    val route: String
) {
    object Home : BottomBarScreen(Icons.Filled.Home, TaskDestinations.List.route)
    object Stopwatch : BottomBarScreen(Icons.Filled.Timer, StopwatchDestinations.List.route)
    // object Settings : BottomBarScreen(Icons.Filled.Settings, SettingsDestinations.Home.route)
}

@Composable
fun TimiBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    TimiBottomBar(
        currentRoute = currentRoute,
        onClick = { screen ->
            navController.navigate(screen.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )
}

@Composable
private fun TimiBottomBar(
    currentRoute: String?,
    onClick: (BottomBarScreen) -> Unit,
    items: List<BottomBarScreen> = values
) {
    BottomNavigation(
        modifier = Modifier.testTag("BottomNav")
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                selected = screen.route == currentRoute,
                onClick = { onClick(screen) },
                icon = {
                    BottomNavIcon(
                        imageVector = screen.imageVector,
                        text = screen.route
                    )
                }
            )
        }
    }
}

@Composable
private fun BottomNavIcon(
    imageVector: ImageVector,
    text: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = imageVector, contentDescription = text)
        Text(text = text)
    }
}

@Preview
@Composable
private fun TimiBottomBarPreview() {
    TimiComposeTheme {
        TimiBottomBar(currentRoute = TaskDestinations.List.route, onClick = { })
    }
}

@Preview
@Composable
private fun TimiBottomBarPreviewDark() {
    TimiComposeTheme(darkTheme = true) {
        TimiBottomBar(currentRoute = TaskDestinations.List.route, onClick = { })
    }
}
