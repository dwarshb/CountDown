package com.dwarshb.countdown

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(context: Context) {
    Column(modifier = Modifier.padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
        var text : String = "";
        EditText(modifier = Modifier.fillMaxWidth(), onValueChanged = {
            text = it
        })
        Button(onClick = {
            val intent = Intent(context,TimerActivity::class.java)
            intent.putExtra("count",text)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }) {
            Text(text = "Submit",modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp))
        }
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
            placeholder = { Text(text = "Enter Time in minutes") },

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