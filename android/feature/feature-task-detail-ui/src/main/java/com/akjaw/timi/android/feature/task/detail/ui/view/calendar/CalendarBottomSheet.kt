package com.akjaw.timi.android.feature.task.detail.ui.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.feature.task.detail.ui.presentation.calendar.CalendarViewModel
import com.akjaw.timi.android.feature.task.detail.ui.presentation.calendar.DayViewState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarBottomSheet(calendarViewModel: CalendarViewModel = getViewModel()) {
    val months = calendarViewModel.viewState.collectAsState().value.months
    val pagerState = rememberPagerState(
        initialPage = CalendarViewModel.CURRENT_MONTH_INDEX
    )

    HorizontalPager(
        count = CalendarViewModel.NUMBER_OF_MONTHS,
        state = pagerState,
        modifier = Modifier.testTag("CalendarPager")
    ) { page ->
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
    days: List<List<DayViewState>>,
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
private fun CalendarDayCard(dayViewState: DayViewState) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(25.dp)
            .toggleable(
                isClicked,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 12.dp)
            ) {
                isClicked = !isClicked
            },
        elevation = 0.dp,
        shape = RoundedCornerShape(50.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isClicked) Color.Cyan else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayViewState.day.toString(),
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
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

private val augustDays: List<List<DayViewState>> = listOf(
    listOf(
        DayViewState(day = 26, month = 7, year = 2021),
        DayViewState(day = 27, month = 7, year = 2021),
        DayViewState(day = 28, month = 7, year = 2021),
        DayViewState(day = 29, month = 7, year = 2021),
        DayViewState(day = 30, month = 7, year = 2021),
        DayViewState(day = 31, month = 7, year = 2021),
        DayViewState(day = 1, month = 8, year = 2021),
    ),
    listOf(
        DayViewState(day = 2, month = 8, year = 2021),
        DayViewState(day = 3, month = 8, year = 2021),
        DayViewState(day = 4, month = 8, year = 2021),
        DayViewState(day = 5, month = 8, year = 2021),
        DayViewState(day = 6, month = 8, year = 2021),
        DayViewState(day = 7, month = 8, year = 2021),
        DayViewState(day = 8, month = 8, year = 2021),
    ),
    listOf(
        DayViewState(day = 9, month = 8, year = 2021),
        DayViewState(day = 10, month = 8, year = 2021),
        DayViewState(day = 11, month = 8, year = 2021),
        DayViewState(day = 12, month = 8, year = 2021),
        DayViewState(day = 13, month = 8, year = 2021),
        DayViewState(day = 14, month = 8, year = 2021),
        DayViewState(day = 15, month = 8, year = 2021),
    ),
    listOf(
        DayViewState(day = 16, month = 8, year = 2021),
        DayViewState(day = 17, month = 8, year = 2021),
        DayViewState(day = 18, month = 8, year = 2021),
        DayViewState(day = 19, month = 8, year = 2021),
        DayViewState(day = 20, month = 8, year = 2021),
        DayViewState(day = 21, month = 8, year = 2021),
        DayViewState(day = 22, month = 8, year = 2021),
    ),
    listOf(
        DayViewState(day = 23, month = 8, year = 2021),
        DayViewState(day = 24, month = 8, year = 2021),
        DayViewState(day = 25, month = 8, year = 2021),
        DayViewState(day = 26, month = 8, year = 2021),
        DayViewState(day = 27, month = 8, year = 2021),
        DayViewState(day = 28, month = 8, year = 2021),
        DayViewState(day = 29, month = 8, year = 2021),
    ),
    listOf(
        DayViewState(day = 30, month = 8, year = 2021),
        DayViewState(day = 31, month = 8, year = 2021),
        DayViewState(day = 1, month = 9, year = 2021),
        DayViewState(day = 2, month = 9, year = 2021),
        DayViewState(day = 3, month = 9, year = 2021),
        DayViewState(day = 4, month = 9, year = 2021),
        DayViewState(day = 5, month = 9, year = 2021),
    ),
)
