package uz.gita.banking.presentation.screen.Splash.vm

import uz.gita.banking.utils.AppViewModel

interface SplashVM : AppViewModel<Nothing, SplashUiState, Nothing> {}




    data class SplashUiState(
        val version: String
    )

