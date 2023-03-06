package uz.gita.banking.presentation.screen.Verify.vm

import uz.gita.banking.data.SignData
import uz.gita.banking.utils.AppViewModel

interface SignVerifyVM : AppViewModel<SignIntent, SignUIState, SignSideEffect> {
}

sealed class SignIntent() {
    data class InfoEdit(val code: String, val signData: SignData) : SignIntent()
    object VerifyButton : SignIntent()
}

sealed interface SignUIState {
    object Default : SignUIState
    object Success : SignUIState
    data class Loading(val isLoad: Boolean = false) : SignUIState
    data class Error(
        val isCode: Boolean = false,
        val CodeMessage: String = "",
    ) : SignUIState

}

sealed interface SignSideEffect {
    data class Message(val text: String) : SignSideEffect
}