package com.dwarshb.countdown

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
fun TimerScreen(context : Context) {
    CountDownTheme {
        // A surface container using the 'background' color from the theme
        Column(modifier = Modifier.fillMaxWidth()
            .fillMaxHeight().background(color = Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CountDownView(context)
        }
    }
}

@ExperimentalTime
@Composable
fun CountDownView(context : Context) {
    var timestamp = 1000L
    var isRunning = false
    var isPaused = true

    Card(
        elevation = 8.dp, modifier = Modifier
            .size(400.dp)
            .fillMaxWidth()
            .padding(16.dp), shape = RoundedCornerShape(corner = CornerSize(8.dp))) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                var text=""
                var counterState: MutableState<String> = remember { mutableStateOf(text) }

                EditText(modifier = Modifier.fillMaxWidth(), onValueChanged = {
                    text = it
                  })

                Button(onClick = {
                    counterState.value = text
                    try {
                        if (text.isNotEmpty() && text.toLong() > 0) {
                            timestamp = text.toLong() * 60L
                            Log.e("CountDownView: ",timestamp.toString() )
                        } else if (isRunning) {
                            Toast.makeText(
                                context,
                                "Please stop the timer before changing the input",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else
                            Toast.makeText(
                                context,
                                "Please Change the Input",
                                Toast.LENGTH_LONG
                            ).show()
                    } catch (e : Exception) {
                        Toast.makeText(
                            context,
                            "${e.message}.\nPlease enter valid Input",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }) {
                    Text(textAlign = TextAlign.Center,
                        text = "Set",modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp))
                }

                Text(
                    text = if (counterState.value.isEmpty())
                        "16:39" else counterState.value,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                )
                Row {
                    TimerControl("Start", onClickedCallback = {
                        if (!isRunning && isPaused) {

                            countdown_timer = object : CountDownTimer(timestamp * 1000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    timestamp = millisUntilFinished / 1000
                                    val minute = (millisUntilFinished / 1000) / 60
                                    val seconds = (millisUntilFinished / 1000) % 60
                                    counterState.value = "$minute:$seconds"
                                    Log.e(
                                        "onTick: ",
                                        counterState.value + "\n" + timestamp / 1000
                                        )
                                    isRunning = true
                                    isPaused = false
                                }

                                override fun onFinish() {
                                    isRunning = false
                                    counterState = mutableStateOf("00:00")
                                }
                            }.start()
                        }
                    })
                    TimerControl(title = "Stop", onClickedCallback = {
                        Log.e("CountDownView: ", "stop")
                        if (isRunning) {
                            countdown_timer.cancel()
                            isPaused = true
                            isRunning = false
                        }
                    })
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

@Composable
fun EditText(modifier: Modifier, onValueChanged: (String) -> Unit) {
    val inputvalue = remember { mutableStateOf(TextFieldValue()) }
    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = modifier,

        // below line is used for specifying
        // vertical arrangement.
        verticalArrangement = Arrangement.Center,

        // below line is used for specifying
        // horizontal arrangement.
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        TextField(
            // below line is used to get
            // value of text field,
            value = inputvalue.value,

            // below line is used to get value in text field
            // on value change in text field.
            onValueChange = { inputvalue.value = it
                            onValueChanged(it.text)},

            // below line is used to add placeholder
            // for our text field.
            placeholder = { Text(text = "Enter Time in minutes (Ex : 4)") },

            // modifier is use to add padding
            // to our text field.
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),

            // keyboard options is used to modify
            // the keyboard for text field.
            keyboardOptions = KeyboardOptions(
                // below line is use for capitalization
                // inside our text field.
                capitalization = KeyboardCapitalization.None,

                // below line is to enable auto
                // correct in our keyboard.
                autoCorrect = true,

                // below line is used to specify our
                // type of keyboard such as text, number, phone.
                keyboardType = KeyboardType.Number,
            ),

            // below line is use to specify
            // styling for our text field value.
            textStyle = TextStyle(color = Color.Black,
                fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            singleLine = true
        )
    }
}