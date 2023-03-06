package uz.gita.banking.presentation.screen.Verify

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.banking.data.SignData
import uz.gita.banking.presentation.extension.Button
import uz.gita.banking.presentation.extension.CustomProgressBar
import uz.gita.banking.presentation.screen.Verify.extension.OTPView
import uz.gita.banking.presentation.screen.Verify.extension.PhoneNumberText
import uz.gita.banking.presentation.screen.Verify.extension.Timer
import uz.gita.banking.presentation.screen.Verify.vm.SignIntent
import uz.gita.banking.presentation.screen.Verify.vm.SignSideEffect
import uz.gita.banking.presentation.screen.Verify.vm.SignUIState
import uz.gita.banking.presentation.screen.Verify.vm.SignVerifyVM
import uz.gita.banking.presentation.screen.Verify.vm.impl.SignVerifyVMImpl


class VerifyScreen(val signData: SignData) : AndroidScreen() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SignVerifyVM = getViewModel<SignVerifyVMImpl>()
        viewModel.collectSideEffect {
            when (it) {
                is SignSideEffect.Message -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
        val uiState = viewModel.collectAsState().value
        SignVerifyScreenComponent(uiState, viewModel::onEventDispatcher)
    }

    @Composable
    fun SignVerifyScreenComponent(
        uiState: SignUIState, onEventDispatcher: (SignIntent) -> Unit
    ) {
        var errorCodeMessage by remember {
            mutableStateOf("")
        }
        when (uiState) {
            is SignUIState.Error -> {
                Toast.makeText(LocalContext.current, uiState.CodeMessage, Toast.LENGTH_SHORT).show()
            }
            is SignUIState.Loading -> {
                CustomProgressBar(progress = uiState.isLoad, Modifier.fillMaxSize())
            }
            is SignUIState.Default -> {}
            is SignUIState.Success -> {}
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            Spacer(Modifier.weight(1f))
            PhoneNumberText(phone = signData.phone)
            Spacer(Modifier.size(20.dp))
            OTPView(
                textSize = 20.sp,
                width = LocalConfiguration.current.screenWidthDp.dp - 20.dp,
                paddingValues = 5.dp,
                codeLength = 6,
                errorMessage = errorCodeMessage
            ) {
                onEventDispatcher(SignIntent.InfoEdit(it, signData))
            }
            Spacer(Modifier.size(20.dp))
            Timer()
            Spacer(Modifier.size(20.dp))
            Button(text = "Verify") {
                onEventDispatcher(SignIntent.VerifyButton)
            }
            Spacer(Modifier.weight(1f))
        }
    }
}