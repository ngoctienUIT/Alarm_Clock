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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tnt.alarmclock.R
import com.tnt.alarmclock.ui.theme.AlarmClockTheme
import com.tnt.alarmclock.utils.listWeekday

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen() {
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
            items(10) { index ->
                ItemAlarm(
                    title = "Work",
                    time = "8:30",
                    check = index % 2 == 0,
                    is24h = false,
                    isAM = true,
                    isOn = index % 2 == 0,
                    weekDay = listOf(index % 2 == 0, true, true, true, true, true, true)
                ) {

                }
            }
        }
    }
}

@Composable
fun ItemAlarm(
    title: String,
    time: String,
    check: Boolean,
    is24h: Boolean = false,
    isAM: Boolean = true,
    isOn: Boolean,
    weekDay: List<Boolean>,
    onChange: (state: Boolean) -> Unit
) {
    Card(
        modifier = if (check) Modifier.padding(end = 8.dp, bottom = 16.dp)
        else Modifier.padding(start = 8.dp, bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF34344A)),
        elevation = CardDefaults.elevatedCardElevation(3.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)) {
            Text(text = title, fontSize = 14.sp, color = Color.White)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = time, fontSize = 36.sp, color = Color.White)
                Spacer(modifier = Modifier.width(5.dp))
                if (!is24h)
                    Text(text = if (isAM) "AM" else "PM", fontSize = 18.sp, color = Color.White)
            }
            BuildWeekday(weekDay)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Switch(
                    checked = isOn,
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
fun BuildWeekday(weekDay: List<Boolean>) {
    if (weekDay.all { it })
        Text(text = "Everyday", color = Color.White)
    else
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
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

@Preview(showBackground = true)
@Composable
fun AlarmScreenPreview() {
    AlarmClockTheme {
        AlarmScreen()
    }
}