package uz.gita.banking.presentation.screen.SignUp


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import uz.gita.banking.presentation.extension.Button
import uz.gita.banking.presentation.extension.CustomProgressBar
import uz.gita.banking.presentation.extension.PasswordEditText
import uz.gita.banking.presentation.extension.PhoneNumberEdit
import uz.gita.banking.presentation.screen.SignUp.extension.*
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpIntent
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpSideEffect
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpUiState
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpVM
import uz.gita.banking.presentation.screen.SignUp.vm.impl.SignUpVMImpl
import uz.gita.banking.ui.theme.Blue
import uz.gita.banking.ui.theme.ErrorColor
import java.time.LocalDate

class SignUpScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: SignUpVM = getViewModel<SignUpVMImpl>()
        val uiState = viewModel.collectAsState().value

        val context = LocalContext.current
        viewModel.collectSideEffect {
            when (it) {
                is SignUpSideEffect.Message -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }

        SignUpComponenet(uiState = uiState, onEventDispatcher = viewModel::onEventDispatcher)
    }


    @Composable
    fun SignUpComponenet(uiState: SignUpUiState, onEventDispatcher: (SignUpIntent) -> Unit) {

        var errorPhoneMessage by remember {
            mutableStateOf("")
        }
        var errorPasswordMessage by remember {
            mutableStateOf("")
        }

        var errorFirstNameMessage by remember {
            mutableStateOf("")
        }
        var errorLastNameMessage by remember {
            mutableStateOf("")
        }
        var errorBirthMessage by remember {
            mutableStateOf("")
        }
        var errorGenderMessage by remember {
            mutableStateOf("")
        }

        var isMale by rememberSaveable {
            mutableStateOf(0)
        }
        when (uiState) {
            is SignUpUiState.Error -> {
                uiState.apply {
//                    errorBirth = isBirth
//                    errorGender = isGender
//                    errorFirstName = isFirstName
//                    errorLastName = isLastName
//                    errorPassword = isPassword
//                    errorPhone = isPhone
                    errorPhoneMessage = phoneMessage
                    errorBirthMessage = birthMessage
                    errorGenderMessage = genderMessage
                    errorFirstNameMessage = firstNameMessage
                    errorLastNameMessage = lastNameMessage
                    errorPasswordMessage = passwordMessage
                }
            }
            is SignUpUiState.Loading -> {
                CustomProgressBar(progress = uiState.isLoad, Modifier.fillMaxSize())
            }
            SignUpUiState.Default -> {}
            SignUpUiState.Success -> {
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {

            Text(
                text = "Sign Up",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            //first Name
            OutlinedEditText(
                hint = "First Name", maxLength = 20, errorMessage = errorFirstNameMessage
            ) {
                onEventDispatcher(SignUpIntent.FirstNameEdit(it))
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            OutlinedEditText(
                hint = "Last Name", maxLength = 20, errorMessage = errorLastNameMessage
            ) {
                onEventDispatcher(SignUpIntent.LastNameEdit(it))
            }
            Spacer(Modifier.height(20.dp))
            //last Name
            DataBirth(hint="Date of Birth",
                errorBirthMessage = errorBirthMessage,
            ) {
                onEventDispatcher(SignUpIntent.DateBirth(it))
            }
            Spacer(Modifier.height(20.dp))
            PhoneNumberEdit(
                hint = "Phone Number", errorMessage = errorPhoneMessage
            ) {
                onEventDispatcher(SignUpIntent.PhoneNumberEdit(it))
            }
            Spacer(Modifier.height(20.dp))
            PasswordEditText(
                hint = "Password",
                maxLength = 25,
                errorMessage = errorPasswordMessage,
            ) {
                onEventDispatcher(SignUpIntent.PassWordEdit(it))
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = "Gender", Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = isMale == 1, colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                ), onClick = {
                 isMale=1
                    onEventDispatcher(SignUpIntent.Gender(true))
                })
                Text(
                    text = "male",
                    Modifier.clickable {
                         isMale=1
                        onEventDispatcher(SignUpIntent.Gender(true))
                    },
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(40.dp))
                RadioButton(selected = isMale == 2, colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                ), onClick = {
                 isMale=2
                    onEventDispatcher(SignUpIntent.Gender(false))
                })
                Text(
                    text = "female",
                    Modifier.clickable {
                     isMale=2
                        onEventDispatcher(SignUpIntent.Gender(false))
                    },
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
            if (errorGenderMessage.isNotEmpty()) {
                Text(
                    text = errorGenderMessage,
                    color = ErrorColor,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(
                Modifier.size(20.dp)
            )
            Button(text = "Verify") {
                onEventDispatcher(SignUpIntent.Verify)
            }
            Spacer(modifier = Modifier.weight(1f))

        }


    }

    @Preview(showBackground = true)
    @Composable
    fun SignUpScreenPreview() {
//        SignUpComponenet()
    }
}
