package com.tnt.alarmclock

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tnt.alarmclock.core.nav_type.AlarmNavType
import com.tnt.alarmclock.presentation.main.MainScreen
import com.tnt.alarmclock.ui.theme.AlarmClockTheme
import com.tnt.alarmclock.core.utils.NavDestinations
import com.tnt.alarmclock.data.local.models.Alarm
import com.tnt.alarmclock.presentation.add_alarm.AddAlarmScreen

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
                composable(NavDestinations.ADD_ALARM_SCREEN) {
                    AddAlarmScreen(navController)
                }
                composable(
                    "${NavDestinations.ADD_ALARM_SCREEN}/{alarm}",
                    arguments = listOf(
                        navArgument("alarm") { type = AlarmNavType() },
                    )
                ) { backStackEntry ->
                    val alarm =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            backStackEntry.arguments?.getParcelable(
                                "alarm",
                                Alarm::class.java
                            )
                        } else {
                            @Suppress("DEPRECATION") backStackEntry.arguments?.getParcelable(
                                "alarm"
                            )
                        }
                    AddAlarmScreen(navController, alarm)
                }
            }
        }
    }
}