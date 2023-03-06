package uz.gita.banking.presentation.screen.Policy.vm.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.data.shp.Shp
import uz.gita.banking.presentation.screen.Policy.vm.PolicyIntent
import uz.gita.banking.presentation.screen.Policy.vm.PolicySideEffect
import uz.gita.banking.presentation.screen.Policy.vm.PolicyUIState
import uz.gita.banking.presentation.screen.Policy.vm.PolicyVM
import uz.gita.banking.presentation.screen.SignIn.SignInScreen
import uz.gita.banking.utils.Const
import uz.gita.news_app_compose.navigation.AppNavigation
import javax.inject.Inject

@HiltViewModel
class PolicyVMImpl @Inject constructor(
    private val shP: Shp,
    private val navigation: AppNavigation
) : PolicyVM, ViewModel() {
    private var confirmPolicy: Boolean = false
    override val container: Container<PolicyUIState, PolicySideEffect> =
        container(PolicyUIState.Default)

    override fun onEventDispatcher(intent: PolicyIntent) = intent {
        when (intent) {
            is PolicyIntent.ConfirmPolicy -> {
                confirmPolicy = intent.isCheck
                reduce { PolicyUIState.PolicyChange(confirmPolicy) }
            }
            PolicyIntent.NextButton -> {
                if (confirmPolicy) {
                    shP.setBool(Const.showPolicy, false)
                    navigation.replaceWith(SignInScreen())
                } else {
                    postSideEffect(
                        PolicySideEffect.Message(
                            "Please Confirm Privacy Policy"
                        )
                    )

                }
            }
        }

    }


}