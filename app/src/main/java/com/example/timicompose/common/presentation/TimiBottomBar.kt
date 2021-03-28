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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.example.timicompose.ui.theme.TimiComposeTheme

private val values = listOf(BottomBarScreen.Home, BottomBarScreen.Timer, BottomBarScreen.Settings)

sealed class BottomBarScreen(
    val imageVector: ImageVector,
    val route: String,
) {
    object Home : BottomBarScreen(Icons.Filled.Home, "Tasks")
    object Timer : BottomBarScreen(Icons.Filled.Timer, "Timer")
    object Settings : BottomBarScreen(Icons.Filled.Settings, "Settings")
}

@Composable
fun TimiBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
    TimiBottomBar(
        currentRoute = currentRoute,
        onClick = { screen ->
            navController.navigate(screen.route) {
                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun TimiBottomBar(
    currentRoute: String?,
    onClick: (BottomBarScreen) -> Unit,
    items: List<BottomBarScreen> = values
) {
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                selected = screen.route == currentRoute,
                onClick = { onClick(screen) },
                icon = {
                    BottomNavIcon(
                        imageVector = screen.imageVector,
                        text = screen.route
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
        TimiBottomBar(currentRoute = BottomBarScreen.Home.route, onClick = { })
    }
}

@Preview
@Composable
fun TimiBottomBarPreviewDark() {
    TimiComposeTheme(darkTheme = true) {
        TimiBottomBar(currentRoute = BottomBarScreen.Home.route, onClick = { })
    }
}
