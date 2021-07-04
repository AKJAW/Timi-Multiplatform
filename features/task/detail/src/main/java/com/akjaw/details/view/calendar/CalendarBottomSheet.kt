package com.akjaw.details.view.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalendarBottomSheet() {
    val months = listOf(
        listOf(31, 1, 2, 3, 4, 5, 6),
        (7..13),
        (14..20),
        (21..27),
        listOf(28, 29, 30, 1, 2, 3, 4),
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "June")
        months.map { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.map {
                    Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                        Text(text = it.toString())
                    }
                }
            }
        }
    }
}
