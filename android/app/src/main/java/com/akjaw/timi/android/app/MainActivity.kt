package com.akjaw.timi.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akjaw.timi.android.core.domain.ActivityInitializerHolder
import com.akjaw.timi.android.core.presentation.SettingsDestinations
import com.akjaw.timi.android.core.presentation.StopwatchDestinations
import com.akjaw.timi.android.core.presentation.TaskDestinations
import com.akjaw.timi.android.core.presentation.TimiBottomBar
import com.akjaw.timi.android.core.view.NavigationHolder
import com.akjaw.timi.android.core.view.theme.ThemeState
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.feature.settings.ui.view.SettingsScreenCreator
import com.akjaw.timi.android.feature.stopwatch.ui.StopwatchScreen
import com.akjaw.timi.android.feature.task.detail.ui.view.TaskDetailScreen
import com.akjaw.timi.android.feature.task.list.ui.view.TaskListScreenCreator
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
                    startDestination = TaskDestinations.List.route
                ) {
                    composable(TaskDestinations.List.route) {
                        taskListScreenCreator.Create(
                            navigationHolder = NavigationHolder(navController)
                        )
                    }
                    composable(TaskDestinations.Details.route) {
                        TaskDetailScreen(navController = navController)
                    }
                    composable(StopwatchDestinations.List.route) {
                        StopwatchScreen(navController = navController)
                    }
                    composable(SettingsDestinations.Home.route) {
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
