package com.example.timicompose.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.timicompose.tasks.presentation.TaskListViewModel
import com.example.timicompose.tasks.view.TaskScreen
import com.example.timicompose.ui.theme.TimiComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val taskListViewModel: TaskListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimiComposeTheme {
                // A surface container using the 'background' color from the theme
                TaskScreen(taskListViewModel)
//                Surface(color = MaterialTheme.colors.background) {
//
//                }
            }
        }
    }
}
