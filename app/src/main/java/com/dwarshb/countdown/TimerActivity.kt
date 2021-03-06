package com.dwarshb.countdown

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dwarshb.countdown.ui.theme.CountDownTheme
import kotlin.time.ExperimentalTime

class TimerActivity : AppCompatActivity() {
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerScreen(baseContext)
        }
    }
}