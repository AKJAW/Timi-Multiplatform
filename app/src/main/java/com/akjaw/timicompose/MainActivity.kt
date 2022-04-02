package com.akjaw.timicompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akjaw.core.common.domain.ActivityInitializer
import com.akjaw.core.common.presentation.BottomBarScreen
import com.akjaw.core.common.presentation.TimiBottomBar
import com.akjaw.core.common.view.NavigationHolder
import com.akjaw.core.common.view.theme.ThemeState
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.settings.view.SettingsScreenCreator
import com.akjaw.task.list.view.TaskListScreenCreator
import com.akjaw.timi.android.stopwatch.ui.StopwatchScreen
import dagger.hilt.android.AndroidEntryPoint
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), KoinComponent {

    @Inject
    lateinit var taskListScreenCreator: TaskListScreenCreator

    private val settingsScreenCreator: SettingsScreenCreator by inject()

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards ActivityInitializer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializers.forEach { it.initialize() }

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
