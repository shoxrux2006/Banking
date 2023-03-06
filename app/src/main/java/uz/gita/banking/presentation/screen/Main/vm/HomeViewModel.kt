package uz.gita.banking.presentation.screen.Home.vm

import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.utils.AppViewModel

interface HomeViewModel : AppViewModel<HomeIntent, HomeUIState, HomeSideEffect> {
    fun LoadCards()
}
sealed interface HomeIntent{

}

sealed interface HomeUIState {
    data class Loading(
        val cardList: List<GetCardsResponse> =
            listOf(
                GetCardsResponse(
                    "",
                    3000000,
                    3,
                    "",
                    2023,
                    9,
                    23,
                    true,
                    ""
                ),
                GetCardsResponse(
                    "",
                    3000000,
                    4,
                    "",
                    2023,
                    9,
                    23,
                    true,
                    ""
                )
            ),
        val isLoading: Boolean
    ) : HomeUIState

    data class Success(val cardList: List<GetCardsResponse>) : HomeUIState

}

sealed interface HomeSideEffect {
    data class Message(val text: String) : HomeSideEffect
}