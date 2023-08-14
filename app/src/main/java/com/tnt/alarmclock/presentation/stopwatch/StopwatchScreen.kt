package com.tnt.alarmclock.presentation.stopwatch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tnt.alarmclock.R
import com.tnt.alarmclock.ui.theme.AlarmClockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreen() {
    Scaffold(
        containerColor = Color(0xFF1D1D23),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1D1D23)
                ),
                title = {
                    Text(
                        text = "Stopwatch",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
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
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
                Text(text = "00", style = style)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = ":", style = style)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "00", style = style)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = ":", style = style)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "00", style = style)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier.size(124.dp, 52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F757)),
                    shape = RoundedCornerShape(26.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Start", color = Color(0xFF34344A))
                }
                Button(
                    modifier = Modifier.size(124.dp, 52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF39393E)),
                    shape = RoundedCornerShape(26.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Lap")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StopwatchScreenPreview() {
    AlarmClockTheme {
        StopwatchScreen()
    }
}