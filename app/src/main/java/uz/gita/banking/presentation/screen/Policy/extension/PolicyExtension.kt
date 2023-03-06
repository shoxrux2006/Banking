package uz.gita.banking.presentation.screen.Policy.extension

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.banking.R
import uz.gita.banking.ui.theme.Blue
import uz.gita.banking.utils.debounced


@Composable
fun CheckBox(isCheck:Boolean,change: (Boolean) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                change.invoke(!isCheck)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        val image =
            if (isCheck) painterResource(id = R.drawable.check_true) else painterResource(id = R.drawable.check_false)
        Image(
            painter = image,
            contentDescription = "check",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(18.dp)
        )
        Spacer(Modifier.size(5.dp))

        Text(
            text = "I confirm the privacy policy",
            color = Blue, fontSize = 18.sp
        )
    }

}

