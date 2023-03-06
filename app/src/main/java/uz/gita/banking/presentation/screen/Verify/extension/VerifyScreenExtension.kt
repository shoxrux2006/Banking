package uz.gita.banking.presentation.screen.Verify.extension

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uz.gita.banking.ui.theme.Blue
import uz.gita.banking.ui.theme.ErrorColor
import uz.gita.banking.ui.theme.TextDefaultColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPView(
    textSize: TextUnit, width: Dp, paddingValues: Dp, codeLength: Int,

    errorMessage: String, change: (String) -> Unit
) {
    val isError = errorMessage.isNotEmpty()
    var textchange by rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester = FocusRequester()
    var selected by rememberSaveable {
        mutableStateOf(0)
    }
    Column {
        BasicTextField(modifier = Modifier
            .width(width)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Backspace) {
                    selected--
                }
                true
            }
            .focusRequester(focusRequester),
            singleLine = true,
            cursorBrush = SolidColor(Color.Red),
            value = textchange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.length <= codeLength) {
                    if (it.length > textchange.length) selected++
                    textchange = it
                    change(textchange)
                }
            },
            decorationBox = {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.width(width)
                ) {
                    repeat(codeLength) {
                        TextItem(
                            isSelected = selected == it,
                            isError = isError,
                            text = textchange,
                            textSize = textSize,
                            position = it,
                            width = width / codeLength - paddingValues,
                            height = width / codeLength - paddingValues,
                        )
                    }
                }
            })
        if (isError) {
            Text(
                text = errorMessage, color = ErrorColor, fontSize = 16.sp
            )
        }
    }
}


@Composable
private fun TextItem(
    isSelected: Boolean,
    isError: Boolean,
    text: String,
    textSize: TextUnit,
    position: Int,
    width: Dp,
    height: Dp
) {
    val textColor = if (isError) Color.Red else {
        if (isSelected) Blue else TextDefaultColor
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(width = width, height = height)) {


            drawRoundRect(
                color = textColor,
                size = Size(width.minus(5.dp).toPx(), height.minus(5.dp).toPx()),
                topLeft = Offset(
                    (((width / 2) - ((width - 5.dp) / 2)).toPx()),
                    (((height / 2) - ((height - 5.dp) / 2)).toPx())
                ),
                style = Stroke(width = 2.dp.toPx()),
                cornerRadius = CornerRadius(
                    x = (width / 20).toPx(), y = (width / 20).toPx()
                )
            )

        }
        Text(
            text = if (text.length > position) text[position].toString() else "",
            fontSize = textSize,
            color = textColor
        )
    }
}


@Composable
fun PhoneNumberText(phone: String) {
    Text(
        text = "we sent verification code on your ${
            phone.substring(
                0, 4
            )
        }*****${phone.substring(9, 13)} phone number",
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun Timer() {
    var time by rememberSaveable {
        mutableStateOf(59)
    }

    LaunchedEffect(key1 = 1) {
        while (time != 0) {
            time--
            delay(1000)
        }
    }
    Text("Time: 0:${if (time < 10) "0${time}" else time}")
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Timer()
}