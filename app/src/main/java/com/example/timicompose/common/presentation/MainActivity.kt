package com.example.timicompose.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.timicompose.tasks.presentation.TaskScreen
import com.example.timicompose.tasks.presentation.TaskViewModel
import com.example.timicompose.ui.theme.TimiComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimiComposeTheme {
                // A surface container using the 'background' color from the theme
                TaskScreen(viewModel)
//                Surface(color = MaterialTheme.colors.background) {
//
//                }
            }
        }
    }
}
