package com.tnt.alarmclock.presentation.clock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tnt.alarmclock.R
import com.tnt.alarmclock.ui.theme.AlarmClockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockScreen() {
    Scaffold(
        containerColor = Color(0xFF1D1D23),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1D1D23)
                ),
                title = { Text(text = "Clock", fontWeight = FontWeight.Bold, color = Color.White) },
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
        LazyColumn(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp
            )
        ) {
            item {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = "8:30", fontSize = 60.sp, color = Color.White)
                        Spacer(modifier = Modifier.width(5.dp))
//                    if (!is24h)
                        Text(text = "PM", fontSize = 30.sp, color = Color.White)
                    }
                    Text(
                        text = "Tue, Nov 30",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                }
            }
            items(10) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF34344A)),
                    elevation = CardDefaults.elevatedCardElevation(3.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "New York",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                            Text(
                                text = "-6h",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFCFCFCF),
                                )
                            )
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = "8:30", fontSize = 36.sp, color = Color.White)
                            Spacer(modifier = Modifier.width(5.dp))
//                    if (!is24h)
                            Text(text = "PM", fontSize = 18.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClockScreenPreview() {
    AlarmClockTheme {
        ClockScreen()
    }
}