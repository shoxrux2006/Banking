package uz.gita.banking.presentation.extension

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import uz.gita.banking.R
import uz.gita.banking.ui.theme.Blue
import uz.gita.banking.ui.theme.ErrorColor
import uz.gita.banking.ui.theme.TextBackground
import uz.gita.banking.utils.PhoneMaskTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEditText(
    hint: String,
    maxLength: Int,

    errorMessage: String,
    onChange: (String) -> Unit,

    ) {
    val isError = errorMessage.isNotEmpty()
    var textChange by rememberSaveable {
        mutableStateOf("")
    }
    var isVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    val icon = if (isVisibility) R.drawable.ic_baseline_visibility_24
    else R.drawable.ic_baseline_visibility_off_24
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(color = MaterialTheme.colorScheme.surface),
            value = textChange,
            singleLine = true,
            onValueChange = {
                if (it.length <= maxLength) {
                    textChange = it
                    onChange(textChange)
                }
            },
            label = {
                Text(text = hint, color = if (isError) ErrorColor else Blue, style = MaterialTheme.typography.bodyMedium)
            },
            trailingIcon = {
                IconButton(onClick = { isVisibility = !isVisibility }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "password icon",
                        tint = if (isError) ErrorColor else Blue
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                errorBorderColor = ErrorColor,
                errorCursorColor = ErrorColor,
                errorLabelColor = ErrorColor,
                textColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = Blue,
                focusedBorderColor = Blue
            ),
            maxLines = 1,
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (isVisibility) VisualTransformation.None else PasswordVisualTransformation()
        )
        if (isError) {
            Text(
                text = errorMessage, color = ErrorColor, style = MaterialTheme.typography.titleSmall
            )

    }
}

@Composable
fun CustomProgressBar(
    progress: Boolean, modifier: Modifier
) {
    if (progress) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(
                strokeWidth = 5.dp, color = Blue
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberEdit(
    hint: String,
    errorMessage: String,
    onChange: (String) -> Unit
) {
    val isError = errorMessage.isNotEmpty()
    var textChange by rememberSaveable {
        mutableStateOf("+998")
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color = MaterialTheme.colorScheme.surface),
        visualTransformation = PhoneMaskTransformation("####(##)-###-##-##"),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_phone_24),
                tint = if (isError) ErrorColor else Blue,
                contentDescription = "logo"
            )
        },

        singleLine = true,
        value = textChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = ErrorColor,
            errorCursorColor = ErrorColor,
            errorLabelColor = ErrorColor,
            textColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = Blue,
            focusedBorderColor = Blue
        ),
        onValueChange = {
            if (it.length <= 13) {
                textChange = it
                onChange(textChange)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        label = {
            Text(text = hint, color = if (isError) ErrorColor else Blue, style = MaterialTheme.typography.bodyMedium)
        },
        maxLines = 1,
        isError = isError
    )
    if (isError) {
        Text(
            text = errorMessage, color = ErrorColor, style = MaterialTheme.typography.titleSmall
        )
    }

}

@Composable
fun Button(text: String, onClick: () -> Unit) {
    Button(
        onClick,
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue
        )
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.bodyMedium)

    }
}

@Composable
fun GitaLogo(logoSize: Dp, textSize: TextUnit, textColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.gita_logo
            ), contentDescription = "logo",
            Modifier
                .height(logoSize)
                .aspectRatio(2f)
        )
        Text(
            text = "GITA BANK",
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontStyle = FontStyle.Italic,
            fontSize = textSize
        )
    }
}
