package uz.gita.banking.presentation.screen.SignUp.vm.impl

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
import uz.gita.banking.data.remote.data.request.AuthRequest.SignUpRequest
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpIntent
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpSideEffect
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpUiState
import uz.gita.banking.presentation.screen.SignUp.vm.SignUpVM
import uz.gita.banking.presentation.screen.Verify.VerifyScreen
import uz.gita.banking.repository.AuthRepository
import uz.gita.banking.utils.NetworkResponse
import uz.gita.news_app_compose.navigation.AppNavigation
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class SignUpVMImpl @Inject constructor(
    private val navigation: AppNavigation, private val repository: AuthRepository
) : SignUpVM, ViewModel() {
    private var phoneChange = ""
    private var passwordChange = ""
    private var birthChange: LocalDate? = null
    private var gender = 0
    private var firstNameChange = ""
    private var lastNameChange = ""
    override fun onEventDispatcher(intent: SignUpIntent) = intent {
        when (intent) {
            is SignUpIntent.DateBirth -> {
                birthChange = intent.date

            }
            is SignUpIntent.FirstNameEdit -> {
                firstNameChange = intent.firstName
            }
            is SignUpIntent.Gender -> {
                //male=1
                //female=2
                if (intent.isMale) {
                    gender = 1
                } else {
                    gender = 2
                }
            }
            is SignUpIntent.LastNameEdit -> {
                lastNameChange = intent.lastName

            }
            is SignUpIntent.PassWordEdit -> {
                passwordChange = intent.password

            }
            is SignUpIntent.PhoneNumberEdit -> {
                phoneChange = intent.phoneNumber

            }
            is SignUpIntent.Verify -> {
                apply {
                    reduce {
                        SignUpUiState.Error(
                            if (firstNameChange.length < 2) "Please enter your firstname" else "",
                            if (lastNameChange.length < 2) "Please enter your lastname" else "",
                            if (passwordChange.length !in (6..20)) "Password length must be between 6 and 20" else "",
                            if (phoneChange.length != 13) "Please enter your phone number" else "",
                            if (birthChange == null) "Please enter your birthday" else "",
                            if (gender == 0) "Please choose your gender" else ""
                        )
                    }


                    if (firstNameChange.length >= 2 && lastNameChange.length >= 2 && passwordChange.length in 6..20 && phoneChange.length == 13 && birthChange != null && 0 != gender) {
                        SignUpRequest(
                            SignUpRequest(
                                passwordChange,
                                gender.toString(),
                                phoneChange,
                                birthChange!!.atStartOfDay(ZoneOffset.UTC).toInstant()
                                    .toEpochMilli().toString(),
                                firstNameChange,
                                lastNameChange
                            )
                        )
                    }
                }
            }
        }

    }

    private fun SignUpRequest(signUpRequest: SignUpRequest) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            repository.signUp(signUpRequest).collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        postSideEffect(SignUpSideEffect.Message(it.message))
                    }
                    is NetworkResponse.Loading -> {
                        reduce { SignUpUiState.Loading(it.isLoading) }
                    }
                    is NetworkResponse.NoConnection -> {
                        postSideEffect(SignUpSideEffect.Message("No internet Connection"))
                    }
                    is NetworkResponse.Success -> {
                        reduce { SignUpUiState.Success }
                        navigation.replaceWith(
                            VerifyScreen(
                                SignData(
                                    SignType.SignUp, it.data!!.token, phoneChange
                                )
                            )
                        )
                    }
                    is NetworkResponse.Failure -> {}
                }
            }
        }

    }


    override val container: Container<SignUpUiState, SignUpSideEffect> =
        container(SignUpUiState.Default)


}