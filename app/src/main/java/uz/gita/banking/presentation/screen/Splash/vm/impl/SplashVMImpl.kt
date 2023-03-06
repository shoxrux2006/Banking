package uz.gita.banking.presentation.screen.Splash.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.BuildConfig
import uz.gita.banking.data.shp.Shp
import uz.gita.banking.presentation.screen.Main.MainScreen
import uz.gita.banking.presentation.screen.Policy.PolicyScreen
import uz.gita.banking.presentation.screen.SignIn.SignInScreen
import uz.gita.banking.presentation.screen.Splash.vm.SplashUiState
import uz.gita.banking.presentation.screen.Splash.vm.SplashVM
import uz.gita.banking.utils.Const
import uz.gita.news_app_compose.navigation.AppNavigation
import javax.inject.Inject

@HiltViewModel
class SplashVMImpl @Inject constructor(
    val navigator: AppNavigation,
    val shP: Shp
) : SplashVM, ViewModel() {
    init {
//        viewModelScope.launch {
//            if (!BuildConfig.DEBUG) {
//                delay(2000)
//            }
//            if (shP.getBool(Const.showPolicy)) {
//                navigator.replaceWith(PolicyScreen())
//            } else {
//                if (!shP.getBool(Const.isFirst)) {
//                    navigator.replaceWith(MainScreen())
//                } else {
//                    navigator.replaceWith(SignInScreen())
//                }
//            }
//        }
    }

    override fun onEventDispatcher(intent: Nothing) = intent {


    }

    override val container: Container<SplashUiState, Nothing> = container(
        SplashUiState(
            BuildConfig.VERSION_NAME
        )
    )


}