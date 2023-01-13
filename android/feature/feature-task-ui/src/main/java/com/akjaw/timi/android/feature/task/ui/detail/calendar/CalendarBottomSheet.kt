package com.akjaw.timi.android.feature.task.ui.detail.calendar

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.CalendarViewState
import com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar.DayViewState
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarBottomSheet(
    state: CalendarViewState,
    onDayClick: (DayViewState) -> Unit
) {
    val months = state.months
    val pagerState = rememberPagerState(
        initialPage = CalendarViewModel.CURRENT_MONTH_INDEX
    )

    HorizontalPager(
        count = CalendarViewModel.NUMBER_OF_MONTHS,
        state = pagerState,
        modifier = Modifier.testTag("CalendarPager").padding(vertical = 8.dp)
    ) { page ->
        CalendarMonth(
            monthName = months[page].monthName,
            days = months[page].calendarDayRows,
            onDayClick = onDayClick,
            modifier = Modifier.testTag("CalendarMonth-$page") // Thanks to this, there are 3 items in the test
        )
    }
}

@Composable
private fun CalendarMonth(
    monthName: String,
    days: List<List<DayViewState>>,
    onDayClick: (DayViewState) -> Unit,
    modifier: Modifier = Modifier,
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
                row.map { dayViewState ->
                    CalendarDayCard(
                        dayViewState = dayViewState,
                        onClick = { onDayClick(dayViewState) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCard(dayViewState: DayViewState, onClick: () -> Unit) {
    // var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(25.dp)
            .clickable(onClick = onClick),
            // .toggleable(
            //     isClicked,
            //     interactionSource = remember { MutableInteractionSource() },
            //     indication = rememberRipple(bounded = false, radius = 12.dp),
            //     onValueChange = {  }
            // ),
        elevation = 0.dp,
        shape = RoundedCornerShape(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
                // .background(if (isClicked) Color.Cyan else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayViewState.day.toString(),
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
    }
}

@Preview
@Composable
fun CalendarMonthPreview() {
    CalendarMonth("August", augustDays, {})
}

@Preview
@Composable
fun CalendarMonthPreviewDark() {
    TimiComposeTheme(darkTheme = true) {
        CalendarMonth("August", augustDays, {})
    }
}

private val augustDays: List<List<DayViewState>> = listOf(
    listOf(
        DayViewState(day = 26, month = 7, year = 2021),
        DayViewState(day = 27, month = 7, year = 2021),
        DayViewState(day = 28, month = 7, year = 2021),
        DayViewState(day = 29, month = 7, year = 2021),
        DayViewState(day = 30, month = 7, year = 2021),
        DayViewState(day = 31, month = 7, year = 2021),
        DayViewState(day = 1, month = 8, year = 2021)
    ),
    listOf(
        DayViewState(day = 2, month = 8, year = 2021),
        DayViewState(day = 3, month = 8, year = 2021),
        DayViewState(day = 4, month = 8, year = 2021),
        DayViewState(day = 5, month = 8, year = 2021),
        DayViewState(day = 6, month = 8, year = 2021),
        DayViewState(day = 7, month = 8, year = 2021),
        DayViewState(day = 8, month = 8, year = 2021)
    ),
    listOf(
        DayViewState(day = 9, month = 8, year = 2021),
        DayViewState(day = 10, month = 8, year = 2021),
        DayViewState(day = 11, month = 8, year = 2021),
        DayViewState(day = 12, month = 8, year = 2021),
        DayViewState(day = 13, month = 8, year = 2021),
        DayViewState(day = 14, month = 8, year = 2021),
        DayViewState(day = 15, month = 8, year = 2021)
    ),
    listOf(
        DayViewState(day = 16, month = 8, year = 2021),
        DayViewState(day = 17, month = 8, year = 2021),
        DayViewState(day = 18, month = 8, year = 2021),
        DayViewState(day = 19, month = 8, year = 2021),
        DayViewState(day = 20, month = 8, year = 2021),
        DayViewState(day = 21, month = 8, year = 2021),
        DayViewState(day = 22, month = 8, year = 2021)
    ),
    listOf(
        DayViewState(day = 23, month = 8, year = 2021),
        DayViewState(day = 24, month = 8, year = 2021),
        DayViewState(day = 25, month = 8, year = 2021),
        DayViewState(day = 26, month = 8, year = 2021),
        DayViewState(day = 27, month = 8, year = 2021),
        DayViewState(day = 28, month = 8, year = 2021),
        DayViewState(day = 29, month = 8, year = 2021)
    ),
    listOf(
        DayViewState(day = 30, month = 8, year = 2021),
        DayViewState(day = 31, month = 8, year = 2021),
        DayViewState(day = 1, month = 9, year = 2021),
        DayViewState(day = 2, month = 9, year = 2021),
        DayViewState(day = 3, month = 9, year = 2021),
        DayViewState(day = 4, month = 9, year = 2021),
        DayViewState(day = 5, month = 9, year = 2021)
    )
)
