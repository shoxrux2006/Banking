package uz.gita.banking.presentation.screen.SignIn.vm.impl

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
import uz.gita.banking.data.remote.data.request.AuthRequest.SignInRequest
import uz.gita.banking.presentation.screen.SignIn.vm.SignInIntent
import uz.gita.banking.presentation.screen.SignIn.vm.SignInSideEffect
import uz.gita.banking.presentation.screen.SignIn.vm.SignInUIState
import uz.gita.banking.presentation.screen.SignIn.vm.SignInVM
import uz.gita.banking.presentation.screen.SignUp.SignUpScreen
import uz.gita.banking.presentation.screen.Verify.VerifyScreen
import uz.gita.banking.repository.AuthRepository
import uz.gita.banking.utils.NetworkResponse
import uz.gita.news_app_compose.navigation.AppNavigation
import javax.inject.Inject

@HiltViewModel
class SignInVMImpl @Inject constructor(
    private val repository: AuthRepository, private val navigation: AppNavigation
) : SignInVM, ViewModel() {


    private var passwordChange = ""
    private var phoneChange = ""

    override fun onEventDispatcher(intent: SignInIntent) = intent {
        when (intent) {
            is SignInIntent.PassWordEdit -> {
                passwordChange = intent.password
            }
            is SignInIntent.PhoneNumberEdit -> {
                phoneChange = intent.phoneNumber
            }
            SignInIntent.SignInButton -> {
                reduce {
                    SignInUIState.Error(
                        if (phoneChange.length != 13) "Please enter your phone number" else "",
                        if (passwordChange.length !in (6..20)) "Password length must be between 6 and 20" else ""
                    )
                }
                if (passwordChange.length in (6..20) && phoneChange.length == 13) {
                    SignInRequest(SignInRequest(passwordChange, phoneChange))
                }
            }
            SignInIntent.SignUpButton -> {
                navigation.replaceWith(SignUpScreen())
            }

        }
    }

    private fun SignInRequest(signInRequest: SignInRequest) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            repository.signIn(signInRequest).collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        postSideEffect(SignInSideEffect.Message(it.message))
                    }
                    is NetworkResponse.Loading -> {
                        reduce { SignInUIState.Loading(it.isLoading) }
                    }
                    is NetworkResponse.NoConnection -> {
                        postSideEffect(SignInSideEffect.Message("No internet Connection"))
                    }
                    is NetworkResponse.Success -> {
                        reduce { SignInUIState.Success }
                        navigation.navigateTo(
                            VerifyScreen(
                                SignData(
                                    SignType.SignIn, it.data!!.token, signInRequest.phone
                                )
                            )
                        )
                    }
                    is NetworkResponse.Failure -> {
                    }
                }
            }
        }

    }

    override val container: Container<SignInUIState, SignInSideEffect> =
        container(SignInUIState.Default)


}