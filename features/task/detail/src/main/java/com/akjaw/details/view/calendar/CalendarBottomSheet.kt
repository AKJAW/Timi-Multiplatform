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
import androidx.hilt.navigation.compose.hiltViewModel
import com.akjaw.details.presenter.calendar.CalendarDay
import com.akjaw.details.presenter.calendar.CalendarViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarBottomSheet(calendarViewModel: CalendarViewModel = hiltViewModel()) {
    val months = calendarViewModel.viewState.value.months
    val pagerState = rememberPagerState(pageCount = 50, initialPage = 40)

    HorizontalPager(state = pagerState) { page ->
        CalendarMonth(months[page].monthName, months[page].calendarDayRows)
    }
}

@Composable
private fun CalendarMonth(monthName: String, days: List<List<CalendarDay>>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = monthName)
        days.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.map { calendarDay ->
                    Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                        Text(text = calendarDay.day.toString())
                    }
                }
            }
        }
    }
}
