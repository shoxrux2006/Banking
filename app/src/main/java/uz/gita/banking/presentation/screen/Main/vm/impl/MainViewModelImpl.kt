package uz.gita.banking.presentation.screen.Main.vm.impl

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.presentation.screen.Main.pages.Home
import uz.gita.banking.presentation.screen.Main.vm.MainIntent
import uz.gita.banking.presentation.screen.Main.vm.MainUIState
import uz.gita.banking.presentation.screen.Main.vm.MainViewModel
import uz.gita.banking.repository.CardRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    val cardRepository: CardRepository
) : MainViewModel, ViewModel() {
    private var currentTab: Tab =Home()
    override fun onEventDispatcher(intent: MainIntent) = intent {
        when (intent) {
            is MainIntent.Navigation -> {
                currentTab=intent.currentTab
                reduce {
                  MainUIState.Default(currentTab)
                }

            }
        }
    }

    override val container: Container<MainUIState, Nothing> =
        container(MainUIState.Default(Home()))


}
