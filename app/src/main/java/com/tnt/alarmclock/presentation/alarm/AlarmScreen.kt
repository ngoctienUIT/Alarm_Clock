package com.tnt.alarmclock.presentation.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tnt.alarmclock.R
import com.tnt.alarmclock.core.utils.NavDestinations
import com.tnt.alarmclock.ui.theme.AlarmClockTheme
import com.tnt.alarmclock.core.utils.listWeekday
import com.tnt.alarmclock.data.local.entity.AlarmEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(navController: NavController, viewModel: AlarmViewModel = hiltViewModel()) {
    val listAlarm by viewModel.listAlarm.collectAsState()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFF1D1D23),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1D1D23)
                ),
                title = { Text(text = "Alarm", fontWeight = FontWeight.Bold, color = Color.White) },
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .clickable { },
                        painter = painterResource(id = R.drawable.more_vert_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(listAlarm.size) { index ->
                ItemAlarm(
                    alarm = listAlarm[index],
                    is24h = true,
                    isPaddingEnd = index % 2 == 0,
                    onChange = { switch ->
                        viewModel.changeSwitch(listAlarm[index].id, switch, context)
                    }
                ) {
                    navController.navigate("${NavDestinations.ADD_ALARM_SCREEN}/${listAlarm[index].toAlarm()}") {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}

@Composable
fun ItemAlarm(
    alarm: AlarmEntity,
    is24h: Boolean = true,
    isPaddingEnd: Boolean,
    onChange: (state: Boolean) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = if (isPaddingEnd) Modifier
            .padding(end = 8.dp, bottom = 16.dp)
            .clickable { onClick() }
        else Modifier
            .padding(start = 8.dp, bottom = 16.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF34344A)),
        elevation = CardDefaults.elevatedCardElevation(3.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)) {
            Text(text = alarm.title, fontSize = 14.sp, color = Color.White)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = alarm.time, fontSize = 36.sp, color = Color.White)
                Spacer(modifier = Modifier.width(5.dp))
                if (!is24h)
                    Text(text = if (true) "AM" else "PM", fontSize = 18.sp, color = Color.White)
            }
            BuildWeekday(alarm.once, alarm.getListWeekday())
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Switch(
                    checked = alarm.isOn,
                    onCheckedChange = onChange,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFFF0F757),
                        checkedIconColor = Color.White,
                        checkedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFF535378),
                        uncheckedIconColor = Color.White,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedThumbColor = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun BuildWeekday(isOnce: Boolean, weekDay: List<Boolean>) {
    if (isOnce) {
        Text(text = "Một lần", color = Color.White)
    } else {
        if (weekDay.all { it })
            Text(text = "Everyday", color = Color.White)
        else
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weekDay.forEachIndexed { index, item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .clip(CircleShape)
                                .background(if (item) Color(0xFFF0F757) else Color.Transparent)
                        )
                        Text(
                            text = listWeekday[index],
                            color = if (item) Color(0xFFF0F757) else Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmScreenPreview() {
    AlarmClockTheme {
        AlarmScreen(rememberNavController())
    }
}