package uz.gita.banking.presentation.screen.Main.vm

import cafe.adriel.voyager.navigator.tab.Tab
import uz.gita.banking.utils.AppViewModel

interface MainViewModel : AppViewModel<MainIntent, MainUIState, Nothing> {

}

sealed interface MainIntent {
    data class Navigation(val currentTab: Tab) : MainIntent
}

sealed interface MainUIState {
    data class Default(val currentTab: Tab) : MainUIState
}

