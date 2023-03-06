package uz.gita.banking.presentation.screen.Policy

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.banking.R
import uz.gita.banking.presentation.extension.Button
import uz.gita.banking.presentation.extension.GitaLogo
import uz.gita.banking.presentation.screen.Policy.extension.CheckBox
import uz.gita.banking.presentation.screen.Policy.vm.PolicyIntent
import uz.gita.banking.presentation.screen.Policy.vm.PolicySideEffect
import uz.gita.banking.presentation.screen.Policy.vm.PolicyUIState
import uz.gita.banking.presentation.screen.Policy.vm.PolicyVM
import uz.gita.banking.presentation.screen.Policy.vm.impl.PolicyVMImpl

class PolicyScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: PolicyVM = getViewModel<PolicyVMImpl>()
        val uiState = viewModel.collectAsState().value
        PolicyScreenComponent(uiState, viewModel::onEventDispatcher)

        viewModel.collectSideEffect {
            when (it) {
                is PolicySideEffect.Message -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Composable
    fun PolicyScreenComponent(uiState: PolicyUIState, onEventDispatcher: (PolicyIntent) -> Unit) {
        var isCheck by remember {
            mutableStateOf(false)
        }
        when (uiState) {
            PolicyUIState.Default -> {}
            is PolicyUIState.PolicyChange -> {
                isCheck = uiState.isCheck
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(20.dp))
            Text(
                text = "Privacy Policy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(Modifier.size(20.dp))
            GitaLogo(logoSize = 20.dp, textSize = 20.sp, textColor = MaterialTheme.colorScheme.tertiary)

            Spacer(Modifier.size(10.dp))
            val scroll = rememberScrollState(0)
            Text(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scroll),
                text = stringResource(R.string.policy),
                color = MaterialTheme.colorScheme.tertiary,
                lineHeight = 22.sp
            )

            Spacer(Modifier.size(15.dp))
            CheckBox(isCheck) {
                onEventDispatcher(PolicyIntent.ConfirmPolicy(it))
            }
            Spacer(Modifier.size(15.dp))
            Button(text = "Next") {
                onEventDispatcher(PolicyIntent.NextButton)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PolicyScreenPreview() {
//        PolicyScreenComponent()
    }
}