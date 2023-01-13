package com.akjaw.timi.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akjaw.timi.android.core.domain.ActivityInitializerHolder
import com.akjaw.timi.android.core.presentation.SettingsDestinations
import com.akjaw.timi.android.core.presentation.StopwatchDestinations
import com.akjaw.timi.android.core.presentation.TaskDestinations
import com.akjaw.timi.android.core.presentation.TimiBottomBar
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.feature.stopwatch.ui.StopwatchScreen
import com.akjaw.timi.android.feature.task.ui.detail.TaskDetailScreen
import com.akjaw.timi.android.feature.task.ui.list.TaskListScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {

    private val activityInitializerHolder: ActivityInitializerHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityInitializerHolder.initializers.forEach { it.initialize() }

        setContent {
            val navController = rememberNavController()
            TimiComposeTheme(/* darkTheme = ThemeState.isDarkTheme.value */) {
                NavHost(
                    navController = navController,
                    startDestination = TaskDestinations.List.route
                ) {
                    composable(TaskDestinations.List.route) {
                        TaskListScreen(navController = navController)
                    }
                    composable(
                        TaskDestinations.Details.route,
                        arguments = listOf(navArgument("taskId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getLong("taskId")
                            ?: throw IllegalArgumentException("Task id cannot be null")
                        TaskDetailScreen(taskId = taskId, navigateBack = { navController.popBackStack() })
                    }
                    composable(StopwatchDestinations.List.route) {
                        StopwatchScreen(navController = navController)
                    }
                    composable(SettingsDestinations.Home.route) {
                        Scaffold(
                            topBar = { TopAppBar(title = { Text(text = "Settings") }) },
                            bottomBar = { TimiBottomBar(navController) }
                        ) {
                            // SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}
