package com.example.anipro.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.anipro.model.AnimeData
import com.example.anipro.navigation.CalendarScreen
import com.example.anipro.utils.BottomAppBarLogin
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@Composable
fun Calendar(model: CalendarViewModel = hiltViewModel(), navController: NavController) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<CalendarStateUI>(
        initialValue = CalendarStateUI.Loading,
        key1 = lifecycle,
        key2 = model
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            model.uiState.collect { value = it }
        }
    }
    Scaffold(
        bottomBar = {
            BottomAppBarLogin(navController = navController, ruta = CalendarScreen)
        }
    ) {
        when (uiState) {
            is CalendarStateUI.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is CalendarStateUI.Success -> CalendarScreen(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                animeEvents = (uiState as CalendarStateUI.Success).animeList
            )

            is CalendarStateUI.Error -> Text("Error")
        }
    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun CalendarScreen(modifier: Modifier, animeEvents: List<AnimeData> = emptyList()) {
    val today = LocalDate.now()
    val events: List<LocalDate> = animeEvents.map {
        LocalDate.parse(it.dateEnd.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    val pagerState =
        rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })
    val currentPage by remember { derivedStateOf { pagerState.currentPage - (Int.MAX_VALUE / 2) } }
    val currentMonth by remember {
        derivedStateOf {
            YearMonth.of(today.year, today.month).plusMonths(currentPage.toLong())
        }
    }
    val movePage = remember { mutableIntStateOf(pagerState.currentPage) }
    LaunchedEffect(movePage.intValue) {
        pagerState.animateScrollToPage(movePage.intValue)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                movePage.intValue = pagerState.currentPage - 1
            }) {
                Text("<")
            }
            Text("${currentMonth.month.name} ${currentMonth.year}")
            Button(onClick = {
                movePage.intValue = pagerState.currentPage + 1
            }) {
                Text(">")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            val displayedMonth = YearMonth.of(today.year, today.month)
                .plusMonths(page.toLong() - (Int.MAX_VALUE / 2))
            CalendarView(
                currentMonth = displayedMonth, events = events
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            items(20) {
                Text("Event $it", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun CalendarView(currentMonth: YearMonth, events: List<LocalDate>) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentMonth.year, currentMonth.month, 1)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // to make Sunday = 0
    val days = (1..daysInMonth).map { LocalDate.of(currentMonth.year, currentMonth.month, it) }

    Column {
        for (week in days.chunked(7)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                color = if (events.contains(date)) MaterialTheme.colorScheme.inversePrimary else Color.Transparent
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicText(text = date.dayOfMonth.toString())
                    }
                }
                repeat(7 - week.size) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}