package com.akjaw.details.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.akjaw.details.view.calendar.CalendarBottomSheet

@Composable
internal fun TaskDetailScreen(navController: NavHostController) {
    CalendarBottomSheet()
}
