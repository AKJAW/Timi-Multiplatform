package com.example.timicompose.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timicompose.ui.theme.TimiComposeTheme

@Composable
fun TaskList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(10.dp, 10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0..5).forEach {
            item {
                Task(it.toString())
            }
        }
    }
}

@Composable
fun Task(name: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { /* Empty */ },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.Green
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = name)
        }
    }
}

@Preview
@Composable
fun TaskListPreview() {
    TimiComposeTheme {
        TaskList(Modifier.background(Color.White))
    }
}
