package com.tnt.alarmclock.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tnt.alarmclock.R
import com.tnt.alarmclock.core.utils.NavDestinations
import com.tnt.alarmclock.presentation.alarm.AlarmScreen
import com.tnt.alarmclock.presentation.clock.ClockScreen
import com.tnt.alarmclock.presentation.stopwatch.StopwatchScreen
import com.tnt.alarmclock.presentation.timer.TimerScreen
import com.tnt.alarmclock.ui.theme.AlarmClockTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val pagerState = rememberPagerState { 4 }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF34344A),
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(90.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TabItem(
                        icon = R.drawable.alarm_clock_icon,
                        label = "Alarm",
                        isSelected = pagerState.currentPage == 0
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = 0)
                        }
                    }
                    TabItem(
                        icon = R.drawable.clock_icon,
                        label = "Clock",
                        isSelected = pagerState.currentPage == 1
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = 1)
                        }
                    }
                    TabItem(
                        icon = R.drawable.hourglass_icon,
                        label = "Timer",
                        isSelected = pagerState.currentPage == 2
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = 2)
                        }
                    }
                    TabItem(
                        icon = R.drawable.stopwatch_icon,
                        label = "Stopwatch",
                        isSelected = pagerState.currentPage == 3
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = 3)
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    navController.navigate(NavDestinations.ADD_ALARM_SCREEN) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        },
    ) {
        HorizontalPager(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize(),
            state = pagerState,
        ) { index ->
            when (index) {
                0 -> AlarmScreen(navController)
                1 -> ClockScreen()
                2 -> TimerScreen()
                3 -> StopwatchScreen()
            }
        }
    }
}

@Composable
fun TabItem(icon: Int, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = if (isSelected) Color(0xFFF0F757) else Color.White,
            contentDescription = "",
        )
        Text(text = label, color = if (isSelected) Color(0xFFF0F757) else Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AlarmClockTheme {
        MainScreen(rememberNavController())
    }
}