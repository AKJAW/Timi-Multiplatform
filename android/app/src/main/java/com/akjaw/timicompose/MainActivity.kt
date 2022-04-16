package com.akjaw.timicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akjaw.settings.view.SettingsScreenCreator
import com.akjaw.timi.android.core.domain.ActivityInitializerHolder
import com.akjaw.timi.android.core.presentation.BottomBarScreen
import com.akjaw.timi.android.core.presentation.TimiBottomBar
import com.akjaw.timi.android.core.view.NavigationHolder
import com.akjaw.timi.android.core.view.theme.ThemeState
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.stopwatch.ui.StopwatchScreen
import com.akjaw.timi.android.task.list.view.TaskListScreenCreator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {

    private val taskListScreenCreator: TaskListScreenCreator by inject()

    private val settingsScreenCreator: SettingsScreenCreator by inject()

    private val activityInitializerHolder: ActivityInitializerHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityInitializerHolder.initializers.forEach { it.initialize() }

        setContent {
            val navController = rememberNavController()
            TimiComposeTheme(darkTheme = ThemeState.isDarkTheme.value) {
                NavHost(
                    navController = navController,
                    startDestination = BottomBarScreen.Home.route
                ) {
                    composable(BottomBarScreen.Home.route) {
                        taskListScreenCreator.Create(
                            navigationHolder = NavigationHolder(navController)
                        )
                    }
                    composable(BottomBarScreen.Stopwatch.route) {
                        StopwatchScreen(navController = navController)
                    }
                    composable(BottomBarScreen.Settings.route) {
                        Scaffold(
                            topBar = { TopAppBar(title = { Text(text = "Settings") }) },
                            bottomBar = { TimiBottomBar(navController) },
                        ) {
                            settingsScreenCreator.Create()
                        }
                    }
                }
            }
        }
    }
}
