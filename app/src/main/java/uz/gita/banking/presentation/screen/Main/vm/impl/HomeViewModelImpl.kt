package uz.gita.banking.presentation.screen.Home.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.banking.presentation.screen.Home.vm.HomeIntent
import uz.gita.banking.presentation.screen.Home.vm.HomeSideEffect
import uz.gita.banking.presentation.screen.Home.vm.HomeUIState
import uz.gita.banking.presentation.screen.Home.vm.HomeViewModel
import uz.gita.banking.repository.CardRepository
import uz.gita.banking.utils.NetworkResponse
import javax.inject.Inject
@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    val cardRepository: CardRepository
) : HomeViewModel, ViewModel() {
    override fun onEventDispatcher(intent: HomeIntent) {

    }

    override val container: Container<HomeUIState, HomeSideEffect> =
        container(HomeUIState.Loading(isLoading = true))

    override fun LoadCards() = intent {
        viewModelScope.launch {
            cardRepository.getCards().collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        postSideEffect(HomeSideEffect.Message(it.message))
                    }
                    is NetworkResponse.Loading -> {
                        reduce { HomeUIState.Loading(isLoading = it.isLoading) }
                    }
                    is NetworkResponse.NoConnection -> {
                        postSideEffect(HomeSideEffect.Message("No internet Connection"))
                    }
                    is NetworkResponse.Success -> {
                        reduce { HomeUIState.Success(it.data!!) }

                    }
                    is NetworkResponse.Failure -> {

                    }
                }
            }
        }
    }
}