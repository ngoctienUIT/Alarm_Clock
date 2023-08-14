package com.tnt.alarmclock

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tnt.alarmclock.presentation.main.MainScreen
import com.tnt.alarmclock.ui.theme.AlarmClockTheme
import com.tnt.alarmclock.utils.NavDestinations

@Composable
fun AlarmClock() {
    AlarmClockTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavDestinations.MAIN_SCREEN,
            ) {
                composable(NavDestinations.MAIN_SCREEN) {
                    MainScreen(navController)
                }
            }
        }
    }
}