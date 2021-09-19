package com.akjaw.details.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akjaw.core.common.view.theme.TimiComposeTheme
import com.akjaw.details.presenter.calendar.CalendarDay
import com.akjaw.details.presenter.calendar.CalendarViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarBottomSheet(calendarViewModel: CalendarViewModel = hiltViewModel()) {
    val months = calendarViewModel.viewState.value.months
    val pagerState = rememberPagerState(
        pageCount = CalendarViewModel.NUMBER_OF_MONTHS,
        initialPage = CalendarViewModel.CURRENT_MONTH_INDEX
    )

    HorizontalPager(state = pagerState, modifier = Modifier.testTag("CalendarPager")) { page ->
        CalendarMonth(
            months[page].monthName,
            months[page].calendarDayRows,
            modifier = Modifier.testTag("CalendarMonth-$page") // Thanks to this, there are 3 items in the test
        )
    }
}

@Composable
private fun CalendarMonth(
    monthName: String,
    days: List<List<CalendarDay>>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .then(modifier)
    ) {
        Text(text = monthName)
        days.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.map { calendarDay ->
                    CalendarDayCard(calendarDay)
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCard(calendarDay: CalendarDay) {
    Card(
        modifier = Modifier
            .size(25.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(50.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = calendarDay.day.toString(),
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
    }
}

@Preview
@Composable
fun CalendarMonthPreview() {
    CalendarMonth("August", augustDays)
}

@Preview
@Composable
fun CalendarMonthPreviewDark() {
    TimiComposeTheme(darkTheme = true) {
        CalendarMonth("August", augustDays)
    }
}

private val augustDays: List<List<CalendarDay>> = listOf(
    listOf(
        CalendarDay(day = 26, month = 7, year = 2021),
        CalendarDay(day = 27, month = 7, year = 2021),
        CalendarDay(day = 28, month = 7, year = 2021),
        CalendarDay(day = 29, month = 7, year = 2021),
        CalendarDay(day = 30, month = 7, year = 2021),
        CalendarDay(day = 31, month = 7, year = 2021),
        CalendarDay(day = 1, month = 8, year = 2021),
    ),
    listOf(
        CalendarDay(day = 2, month = 8, year = 2021),
        CalendarDay(day = 3, month = 8, year = 2021),
        CalendarDay(day = 4, month = 8, year = 2021),
        CalendarDay(day = 5, month = 8, year = 2021),
        CalendarDay(day = 6, month = 8, year = 2021),
        CalendarDay(day = 7, month = 8, year = 2021),
        CalendarDay(day = 8, month = 8, year = 2021),
    ),
    listOf(
        CalendarDay(day = 9, month = 8, year = 2021),
        CalendarDay(day = 10, month = 8, year = 2021),
        CalendarDay(day = 11, month = 8, year = 2021),
        CalendarDay(day = 12, month = 8, year = 2021),
        CalendarDay(day = 13, month = 8, year = 2021),
        CalendarDay(day = 14, month = 8, year = 2021),
        CalendarDay(day = 15, month = 8, year = 2021),
    ),
    listOf(
        CalendarDay(day = 16, month = 8, year = 2021),
        CalendarDay(day = 17, month = 8, year = 2021),
        CalendarDay(day = 18, month = 8, year = 2021),
        CalendarDay(day = 19, month = 8, year = 2021),
        CalendarDay(day = 20, month = 8, year = 2021),
        CalendarDay(day = 21, month = 8, year = 2021),
        CalendarDay(day = 22, month = 8, year = 2021),
    ),
    listOf(
        CalendarDay(day = 23, month = 8, year = 2021),
        CalendarDay(day = 24, month = 8, year = 2021),
        CalendarDay(day = 25, month = 8, year = 2021),
        CalendarDay(day = 26, month = 8, year = 2021),
        CalendarDay(day = 27, month = 8, year = 2021),
        CalendarDay(day = 28, month = 8, year = 2021),
        CalendarDay(day = 29, month = 8, year = 2021),
    ),
    listOf(
        CalendarDay(day = 30, month = 8, year = 2021),
        CalendarDay(day = 31, month = 8, year = 2021),
        CalendarDay(day = 1, month = 9, year = 2021),
        CalendarDay(day = 2, month = 9, year = 2021),
        CalendarDay(day = 3, month = 9, year = 2021),
        CalendarDay(day = 4, month = 9, year = 2021),
        CalendarDay(day = 5, month = 9, year = 2021),
    ),
)
