package com.example.anipro.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import coil.compose.AsyncImage
import com.example.anipro.model.AnimeData
import com.example.anipro.navigation.CalendarScreen
import com.example.anipro.navigation.InfoScreen
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
            is CalendarStateUI.Success -> CalendarList(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                animeEvents = (uiState as CalendarStateUI.Success).animeList,
                animesShowList = (uiState as CalendarStateUI.Success).animesShowList,
                navigateToInfo = { id ->
                    navController.navigate(InfoScreen(id))
                },
                model = model
            )

            is CalendarStateUI.Error -> Text("Error")
        }
    }
}

@Composable
fun CalendarList(
    modifier: Modifier,
    animeEvents: List<AnimeData> = emptyList(),
    animesShowList: List<AnimeData> = emptyList(),
    navigateToInfo: (Int) -> Unit = {},
    model: CalendarViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
        val selectedTab by remember { derivedStateOf { pagerState.currentPage } }
        val movePage = remember { mutableIntStateOf(0) } // Initialize with the new initialPage

        LaunchedEffect(movePage.intValue) {
            pagerState.animateScrollToPage(movePage.intValue)
        }

        // val coroutineScope = rememberCoroutineScope() // Not strictly needed here

        TabRow(
            selectedTabIndex = selectedTab, // This is now 0 or 1
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = {
                    movePage.intValue = 0 // Scroll Pager to the 'List' page (index 0)
                },
                text = { Text("List", style = MaterialTheme.typography.bodyLarge) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = {
                    movePage.intValue = 1 // Scroll Pager to the 'Calendar' page (index 1)
                },
                text = { Text("Calendar", style = MaterialTheme.typography.bodyLarge) }
            )
        }

        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page -> // page will be 0 or 1
            if (page == 1) { // Calendar View (index 1)
                CalendarScreen(
                    modifier = Modifier.fillMaxSize(),
                    animeEvents = animeEvents, // Pass full list of events
                    animesShowList = animesShowList,
                    navigateToInfo = navigateToInfo,
                    model = model
                )
            } else {
                AnimeList(animesShowList = animeEvents, navigateToInfo = navigateToInfo, modifier = Modifier.fillMaxSize()
                )
            }
        }
    }


}


@Composable
fun CalendarScreen(
    modifier: Modifier,
    animeEvents: List<AnimeData> = emptyList(),
    animesShowList: List<AnimeData> = emptyList(),
    navigateToInfo: (Int) -> Unit = {},
    model: CalendarViewModel
) {
    val today = LocalDate.now()
    val events: List<LocalDate> = animeEvents.map {
        LocalDate.parse(it.dateEnd.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }


    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })
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
                currentMonth = displayedMonth, events = events, model::onDateSelected
            )
        }
        AnimeList(animesShowList = animesShowList, navigateToInfo = navigateToInfo, modifier = Modifier.weight(1f))
    }
}



@Composable
fun AnimeList( animesShowList: List<AnimeData>, navigateToInfo: (Int) -> Unit = {}, modifier: Modifier = Modifier) {
    val animeSorted = animesShowList.sortedBy { it.dateEnd }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        items(animeSorted.size) { index ->
            Column {
                if (index == 0 || animeSorted[index].dateEnd != animeSorted[index - 1].dateEnd) {
                    Text(text = "${animeSorted[index].dateEnd}",modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable {
                            navigateToInfo(animeSorted[index].id)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = animeSorted[index].title, modifier = Modifier.padding(8.dp).fillMaxWidth(
                        0.7F
                    ))
                    AsyncImage(
                        model = animeSorted[index].image,
                        contentDescription = "Anime Image",
                        modifier = Modifier.fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    )
                }
            }
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
fun CalendarView(
    currentMonth: YearMonth,
    events: List<LocalDate>,
    onClickDate: (LocalDate) -> Unit = {}
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentMonth.year, currentMonth.month, 1)
    // Sunday is 7, Monday is 1. We want Sunday to be 0.
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7)
    val days = (1..daysInMonth).map { LocalDate.of(currentMonth.year, currentMonth.month, it) }
    val today = LocalDate.now()

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
        // Add headers for days of the week
        Row(modifier = Modifier.fillMaxWidth()) {
            val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            daysOfWeek.forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = day, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }

        // Add empty cells for days before the first day of the month
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(startDayOfWeek) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(2.dp) // Reduced padding for tighter packing
                )
            }

            // Fill the rest of the first week
            days.take(7 - startDayOfWeek).forEach { date ->
                DateCell(date = date, isEventDay = events.contains(date), isToday = date.isEqual(today), onClickDate = onClickDate)
            }
        }

        // Fill the remaining weeks
        days.drop(7 - startDayOfWeek).chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    DateCell(date = date, isEventDay = events.contains(date), isToday = date.isEqual(today), onClickDate = onClickDate)
                }
                // Add empty spacers if the week is not full
                if (week.size < 7) {
                    repeat(7 - week.size) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp) // Reduced padding
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DateCell(date: LocalDate, isEventDay: Boolean, isToday: Boolean, onClickDate: (LocalDate) -> Unit) {
    val backgroundColor = when {
        isEventDay -> MaterialTheme.colorScheme.primaryContainer
        isToday -> MaterialTheme.colorScheme.secondaryContainer // Subtle highlight for today
        else -> MaterialTheme.colorScheme.surface
    }
    val textColor = if (isEventDay || isToday) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f) // Make cells square
            .padding(3.dp) // Adjusted padding
            .clip(RoundedCornerShape(12.dp)) // More rounded corners for a softer look
            .background(backgroundColor)
            .clickable { onClickDate(date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium, // Consistent font style
            color = textColor
        )
    }
}