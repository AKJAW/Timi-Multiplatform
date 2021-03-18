package com.example.timicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.timicompose.ui.tasks.Task
import com.example.timicompose.ui.tasks.TaskList
import com.example.timicompose.ui.theme.TimiComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimiComposeTheme {
                // A surface container using the 'background' color from the theme
                TaskList()
//                Surface(color = MaterialTheme.colors.background) {
//
//                }
            }
        }
    }
}
