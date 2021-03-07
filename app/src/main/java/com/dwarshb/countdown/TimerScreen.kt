package com.dwarshb.countdown

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.dwarshb.countdown.ui.theme.CountDownTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

lateinit var countdown_timer: CountDownTimer

@ExperimentalTime
@Composable
fun TimerScreen(count: String) {
    CountDownTheme {
        // A surface container using the 'background' color from the theme
        CountDownView(count)
    }
}

@ExperimentalTime
@Composable
fun CountDownView(text:String) {
    var timestamp = 1000L
    if (text.isNotEmpty()) {
        try {
            timestamp = text.toLong()*60L
            Log.e("CountDownView: ",timestamp.toString() )
        } catch (e : Exception) {
            Log.e("CountDownView: ",e.message!! )
        }
    }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
        Card(
            elevation = 8.dp, modifier = Modifier
                .size(250.dp)
                .padding(16.dp),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var counterState: MutableState<String> = remember { mutableStateOf(text) }
                Text(text = "Timer",modifier = Modifier.weight(1f))
                Text(
                    text = counterState.value,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                )
                Row {
                    TimerControl("Start", onClickedCallback = {
                        countdown_timer = object : CountDownTimer(timestamp * 1000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                timestamp = millisUntilFinished/1000
                                val minute = (millisUntilFinished / 1000) / 60
                                val seconds = (millisUntilFinished / 1000) % 60
                                counterState.value = "$minute:$seconds"
                                Log.e("onTick: ", counterState.value+"\n"+timestamp/1000)
                            }

                            override fun onFinish() {
                                counterState = mutableStateOf("00:00")
                            }
                        }.start()
                    })
                    TimerControl(title = "Stop", onClickedCallback = {
                        Log.e("CountDownView: ", "stop")
                        countdown_timer.cancel()
                    })
                }
            }
        }
    }
}
@Composable
fun TimerControl(title : String,onClickedCallback:() -> Unit) {
    Button(onClick = onClickedCallback,modifier = Modifier.padding(start= 8.dp, end=8.dp)) {
        Text(text = title)
    }
}