package uz.gita.banking.presentation.screen.Policy.vm

import uz.gita.banking.utils.AppViewModel

interface PolicyVM : AppViewModel<PolicyIntent, PolicyUIState, PolicySideEffect> {}

sealed class PolicyIntent() {
    object NextButton : PolicyIntent()
    data class ConfirmPolicy(val isCheck: Boolean) : PolicyIntent()
}

sealed class PolicyUIState {
    object Default : PolicyUIState()
    data class PolicyChange(val isCheck: Boolean):PolicyUIState()
}


sealed interface PolicySideEffect {
    data class Message(val text: String) : PolicySideEffect
}
