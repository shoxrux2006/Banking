package uz.gita.banking.presentation.screen.SignIn

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.banking.data.`var`.TextStyle
import uz.gita.banking.data.`var`.ThemeType
import uz.gita.banking.presentation.extension.Button
import uz.gita.banking.presentation.extension.CustomProgressBar
import uz.gita.banking.presentation.extension.PasswordEditText
import uz.gita.banking.presentation.extension.PhoneNumberEdit
import uz.gita.banking.presentation.screen.SignIn.vm.SignInIntent
import uz.gita.banking.presentation.screen.SignIn.vm.SignInSideEffect
import uz.gita.banking.presentation.screen.SignIn.vm.SignInUIState
import uz.gita.banking.presentation.screen.SignIn.vm.SignInVM
import uz.gita.banking.presentation.screen.SignIn.vm.impl.SignInVMImpl
import uz.gita.banking.ui.theme.ApplicatiomTheme
import uz.gita.banking.ui.theme.BankingTheme
import uz.gita.banking.ui.theme.Blue

class SignInScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SignInVM = getViewModel<SignInVMImpl>()

        viewModel.collectSideEffect {
            when (it) {
                is SignInSideEffect.Message -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        val uiState = viewModel.collectAsState().value
        SignInScreenComponenet(uiState, viewModel::onEventDispatcher)
    }

    @Composable
    fun SignInScreenComponenet(uiState: SignInUIState, onEventDispatcher: (SignInIntent) -> Unit) {
        var errorPhoneMessage by remember {
            mutableStateOf("")
        }
        var errorPasswordMessage by remember {
            mutableStateOf("")
        }
        var context = LocalContext.current
        when (uiState) {
            is SignInUIState.Error -> {
                errorPasswordMessage = uiState.passwordMessage
                errorPhoneMessage = uiState.phoneMessage
            }
            is SignInUIState.Loading -> {
                CustomProgressBar(progress = uiState.isLoad, Modifier.fillMaxSize())
            }

            else -> {}
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .size(130.dp)
            )
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            PhoneNumberEdit(
                hint = "Phone Number", errorMessage = errorPhoneMessage
            ) {
                onEventDispatcher(SignInIntent.PhoneNumberEdit(it))
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .size(20.dp)
            )
            PasswordEditText(
                hint = "Password",
                maxLength = 20,
                errorMessage = errorPasswordMessage,
            ) {
                onEventDispatcher(SignInIntent.PassWordEdit(it))
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .size(20.dp)
            )
            Row {
                Text(text = "Don't have account:", color = MaterialTheme.colorScheme.tertiary)
                Text(
                    text = "create one!", Modifier.clickable {
                        onEventDispatcher(SignInIntent.SignUpButton)
                    }, color = Blue
                )
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .size(40.dp)
            )

            Button(text = "Sign In") {
                onEventDispatcher(SignInIntent.SignInButton)
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(3f)
        )
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun SignInPreview() {
        BankingTheme {
//            SignInScreenComponenet()
        }
    }
}
