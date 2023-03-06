package uz.gita.banking.presentation.screen.SignUp.extension

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import uz.gita.banking.R
import uz.gita.banking.ui.theme.Blue
import uz.gita.banking.ui.theme.ErrorColor
import uz.gita.banking.ui.theme.TextBackground
import uz.gita.banking.utils.PhoneMaskTransformation
import uz.gita.banking.utils.l
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedEditText(
    hint: String,
    maxLength: Int,
    errorMessage: String,
    placeHolderRes: Int = R.drawable.ic_baseline_person_24,
    onChange: (String) -> Unit
) {
    val isError = errorMessage.isNotEmpty()
    var textChange by rememberSaveable {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color = MaterialTheme.colorScheme.surface),
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
        leadingIcon = {
            Icon(
                painter = painterResource(placeHolderRes),
                contentDescription = "logo",
                tint = if (isError) ErrorColor else Blue
            )
        },
        onValueChange = {
            if (it.length <= maxLength) {
                textChange = it
                onChange(textChange)
            }
        },
        label = {
            Text(
                text = hint,
                color = if (isError) ErrorColor else Blue,
                style = MaterialTheme.typography.bodyMedium
            )
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun DataBirth(
    hint: String,
    errorBirthMessage: String, onChange: (LocalDate) -> Unit
) {
    val errorBirth = errorBirthMessage.isNotEmpty()
    var selectedDates: LocalDate? by rememberSaveable {
        mutableStateOf(null)
    }
    var state = rememberSheetState(visible = false, onCloseRequest = {

    })
    CalendarDialog(
        state = state,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date { newDates ->
            selectedDates = newDates
            onChange(selectedDates!!)
        },
    )
    Text(text = if (selectedDates != null) {
        "${selectedDates!!.dayOfMonth}-${
            selectedDates!!.month.toString().substring(0, 3)
        }-${selectedDates!!.year}"
    } else hint,
        color = Blue,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                state.show()
            }
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(14.dp),
        style = MaterialTheme.typography.bodyMedium)
    if (errorBirth) {
        Text(
            text = errorBirthMessage,
            color = ErrorColor,
            style = MaterialTheme.typography.titleSmall
        )
    }

}


@Preview(showBackground = true)
@Composable
fun Preview() {


}