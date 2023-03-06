package uz.gita.banking.presentation.screen.SignUp.vm

import uz.gita.banking.utils.AppViewModel
import java.time.LocalDate

interface SignUpVM : AppViewModel<SignUpIntent, SignUpUiState, SignUpSideEffect> {}


sealed class SignUpIntent() {
    data class PhoneNumberEdit(val phoneNumber: String) : SignUpIntent()
    data class PassWordEdit(val password: String) : SignUpIntent()
    data class FirstNameEdit(val firstName: String) : SignUpIntent()
    data class LastNameEdit(val lastName: String) : SignUpIntent()
    data class DateBirth(val date: LocalDate) : SignUpIntent()
    data class Gender(val isMale: Boolean) : SignUpIntent()
    object Verify : SignUpIntent()
}

sealed interface SignUpUiState {
    data class Loading(val isLoad: Boolean = false) : SignUpUiState
    data class Error(
        val firstNameMessage: String = "",
        val lastNameMessage: String = "",
        val passwordMessage: String = "",
        val phoneMessage: String = "",
        val birthMessage: String = "",
        val genderMessage: String = "",
    ) : SignUpUiState

    object Default : SignUpUiState
    object Success : SignUpUiState

}

sealed interface SignUpSideEffect {
    data class Message(val text: String) : SignUpSideEffect
}






