package uz.gita.banking.presentation.screen.SignIn.vm

import uz.gita.banking.utils.AppViewModel


interface SignInVM : AppViewModel<SignInIntent, SignInUIState, SignInSideEffect> {

}

sealed interface SignInIntent {
    data class PhoneNumberEdit(val phoneNumber: String) : SignInIntent
    data class PassWordEdit(val password: String) : SignInIntent
    object SignInButton : SignInIntent
    object SignUpButton : SignInIntent
}

sealed interface SignInUIState {
    object Default : SignInUIState
    object Success : SignInUIState
    data class Loading(val isLoad: Boolean = false) : SignInUIState
    data class Error(
        val phoneMessage: String = "",
        val passwordMessage: String = ""
    ) : SignInUIState

}

sealed interface SignInSideEffect {
    data class Message(val text: String) : SignInSideEffect
}
