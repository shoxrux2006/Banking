package uz.gita.banking.presentation.screen.Verify.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.data.SignData
import uz.gita.banking.data.`var`.SignType
import uz.gita.banking.data.remote.data.request.AuthRequest.VerifySignInRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.VerifySignUpRequest
import uz.gita.banking.presentation.screen.Main.MainScreen
import uz.gita.banking.presentation.screen.Verify.vm.SignIntent
import uz.gita.banking.presentation.screen.Verify.vm.SignSideEffect
import uz.gita.banking.presentation.screen.Verify.vm.SignUIState
import uz.gita.banking.presentation.screen.Verify.vm.SignVerifyVM
import uz.gita.banking.repository.AuthRepository
import uz.gita.banking.utils.NetworkResponse
import uz.gita.news_app_compose.navigation.AppNavigation
import javax.inject.Inject

@HiltViewModel
class SignVerifyVMImpl @Inject constructor(
    val repository: AuthRepository,
    val navigation: AppNavigation
) : SignVerifyVM, ViewModel() {
    private var code = ""
    private var signData = SignData(SignType.SignUp, "", "")
    override fun onEventDispatcher(intent: SignIntent) = intent {
        when (intent) {
            is SignIntent.InfoEdit -> {
                code = intent.code
                signData = intent.signData
            }
            is SignIntent.VerifyButton -> {
                if (code.length == 6) {
                    VerifySign(code, signData)
                } else {
                    reduce { SignUIState.Error(true, "Please enter code!") }
                }
            }
        }
    }

    private fun VerifySign(code: String, signData: SignData) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            if (signData.signType == SignType.SignIn) {
                repository.verifySignIn(VerifySignInRequest(code, signData.token)).collect {
                    when (it) {
                        is NetworkResponse.Error -> {
                            postSideEffect(SignSideEffect.Message(it.message))
                        }
                        is NetworkResponse.Loading -> {
                            reduce { SignUIState.Loading(it.isLoading) }
                        }
                        is NetworkResponse.NoConnection -> {
                            postSideEffect(SignSideEffect.Message("No internet Connection"))
                        }
                        is NetworkResponse.Success -> {
                            navigation.replaceWith(MainScreen())
                        }
                        is NetworkResponse.Failure -> {
                        }
                    }
                }
            } else {
                repository.verifySignUp(VerifySignUpRequest(code, signData.token)).collect {
                    when (it) {
                        is NetworkResponse.Error -> {
                            postSideEffect(SignSideEffect.Message(it.message))
                        }
                        is NetworkResponse.Loading -> {
                            reduce { SignUIState.Loading(it.isLoading) }
                        }
                        is NetworkResponse.NoConnection -> {
                            postSideEffect(SignSideEffect.Message("No internet Connection"))
                        }
                        is NetworkResponse.Success -> {
                            navigation.replaceWith(MainScreen())
                        }
                        is NetworkResponse.Failure -> {
                        }
                    }
                }
            }
        }
    }


    override val container: Container<SignUIState, SignSideEffect> =
        container(SignUIState.Default)

}