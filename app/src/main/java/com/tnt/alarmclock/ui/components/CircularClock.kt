package com.tnt.alarmclock.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularClock(
    hourSize: Int,
    initialHour: Int,
    height: Dp = 250.dp,
    onChange: (number: Int) -> Unit
) {
    val cellSize = height / 3
    val cellTextSize = LocalDensity.current.run { (cellSize / 2f).toSp() }

    val hourOffset = if (hourSize == 12) 1 else 0
    val expandedSize = hourSize * 10_000_000
    val initialListPoint = expandedSize / 2
    val targetIndex = initialListPoint + initialHour - 1

    val scrollState = rememberLazyListState(targetIndex)
    val hour by remember { derivedStateOf { (scrollState.firstVisibleItemIndex + 1) % hourSize } }

    if (!scrollState.isScrollInProgress) {
        Log.e("FocusedHour", "${hour + hourOffset}")
        onChange(hour + hourOffset)
    }

    LaunchedEffect(Unit) {
        scrollState.scrollToItem(targetIndex - hourOffset)
    }

    Box(
        modifier = Modifier
            .height(height)
            .wrapContentWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .wrapContentWidth(),
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)
        ) {
            items(expandedSize) {
                val num = (it % hourSize) + hourOffset
                Box(
                    modifier = Modifier
                        .size(cellSize),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d", num),
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = cellTextSize
                        )
                    )
                }
            }
        }
    }
}