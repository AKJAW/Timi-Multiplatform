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
import com.akjaw.core.common.presentation.BottomBarScreen
import com.akjaw.core.common.presentation.TimiBottomBar
import com.akjaw.core.common.view.NavigationHolder
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.settings.SettingsScreenCreator
import com.akjaw.stopwatch.view.StopwatchScreenCreator
import com.akjaw.task.api.view.TaskListScreenCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var taskListScreenCreator: TaskListScreenCreator

    @Inject
    lateinit var stopwatchScreenCreator: StopwatchScreenCreator

    @Inject
    lateinit var settingsScreenCreator: SettingsScreenCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TimiComposeTheme {
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
                        stopwatchScreenCreator.Create(
                            navigationHolder = NavigationHolder(navController)
                        )
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
