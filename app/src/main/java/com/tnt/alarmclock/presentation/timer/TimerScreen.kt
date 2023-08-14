package com.tnt.alarmclock.presentation.timer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tnt.alarmclock.R
import com.tnt.alarmclock.ui.theme.AlarmClockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
    Scaffold(
        containerColor = Color(0xFF1D1D23),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1D1D23)
                ),
                title = { Text(text = "Timer", fontWeight = FontWeight.Bold, color = Color.White) },
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
        it
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    AlarmClockTheme {
        TimerScreen()
    }
}