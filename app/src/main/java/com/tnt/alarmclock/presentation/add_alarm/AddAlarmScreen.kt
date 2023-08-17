package com.tnt.alarmclock.presentation.add_alarm

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tnt.alarmclock.R
import com.tnt.alarmclock.data.local.models.Alarm
import com.tnt.alarmclock.ui.components.CircularClock
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.tnt.alarmclock.core.utils.listWeekdayFull
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(
    navController: NavController,
    alarm: Alarm? = null,
    viewModel: AddAlarmViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val sheetForOnceState = rememberModalBottomSheetState()
    val sheetForRepeatState = rememberModalBottomSheetState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (alarm != null) {
            viewModel.title = alarm.title
            viewModel.once = alarm.once
            viewModel.listRepeat.clear()
            viewModel.listRepeat.addAll(alarm.getListWeek())
        }
        Log.d("once", viewModel.once.toString())
    }

    if (alarm != null) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = LocalTime.parse(alarm.time, formatter)
        viewModel.hour = time.hour
        viewModel.minute = time.minute
    }

    if (viewModel.showSheetForOnce) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.showSheetForOnce = false },
            sheetState = sheetForOnceState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            SheetForOnce(viewModel.once) {
                viewModel.once = it
                if (!viewModel.once) {
                    viewModel.showSheetForRepeat = true
                    viewModel.showSheetForOnce = false
                }
            }
        }
    }

    if (viewModel.showSheetForRepeat) {
        SheetForRepeat(
            listRepeat = viewModel.listRepeat,
            sheetState = sheetForRepeatState,
            onDismiss = {
                viewModel.showSheetForRepeat = false
            }
        ) { index, state ->
            viewModel.listRepeat[index] = state
        }
    }

    Scaffold(
        containerColor = Color(0xFF1D1D23),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1D1D23)
                ),
                title = {
                    Text(
                        text = "Add Alarm",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(30.dp)
                            .clickable {
                                viewModel.updateAlarm(alarm?.id, context)
                                navController.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.check_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(30.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.close_icon),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding() + 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularClock(hourSize = 24, initialHour = viewModel.hour) { time ->
                    viewModel.hour = time
                }
                Spacer(modifier = Modifier.width(10.dp))
                CircularClock(hourSize = 60, initialHour = viewModel.minute) { time ->
                    viewModel.minute = time
                }
            }
            Spacer(modifier = Modifier.padding(top = 50.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                value = viewModel.title,
                placeholder = { Text(text = "Title") },
                shape = RoundedCornerShape(16.dp),
                onValueChange = { text -> viewModel.title = text },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF34344A),
                    unfocusedContainerColor = Color(0xFF34344A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.White.copy(0.5f),
                    unfocusedPlaceholderColor = Color.White.copy(0.5f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                )
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .clickable {
                        scope.launch {
                            viewModel.showSheetForOnce = true
                        }
                    }
            ) {
                Text(
                    text = "Repeat",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (viewModel.once) "Một lần" else "Lặp lại",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.arrow_forward_icon),
                    contentDescription = "",
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun SheetForOnce(once: Boolean, onChange: (once: Boolean) -> Unit) {
    Column {
        SheetItem(title = "Một lần", isCheck = once) {
            onChange(true)
        }
        SheetItem(title = "Lặp lại", isCheck = !once) {
            onChange(false)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetForRepeat(
    listRepeat: List<Boolean>,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onChange: (index: Int, state: Boolean) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        LazyColumn {
            items(listRepeat.size) { index ->
                SheetItem(title = listWeekdayFull[index], isCheck = listRepeat[index]) {
                    onChange(index, !listRepeat[index])
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun SheetItem(title: String, isCheck: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable { onClick() }
    ) {
        Text(text = title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        if (isCheck)
            Icon(
                painter = painterResource(id = R.drawable.check_icon),
                contentDescription = "",
                tint = Color(0xFFF0F757)
            )
    }
}