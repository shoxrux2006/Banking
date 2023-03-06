package uz.gita.banking.presentation.screen.Main.pages


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.banking.R
import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.presentation.screen.Home.vm.HomeIntent
import uz.gita.banking.presentation.screen.Home.vm.HomeUIState
import uz.gita.banking.presentation.screen.Home.vm.HomeViewModel
import uz.gita.banking.presentation.screen.Home.vm.impl.HomeViewModelImpl
import uz.gita.banking.presentation.screen.Main.extension.CardViewPager
import uz.gita.banking.presentation.screen.Main.extension.CircleButton
import uz.gita.banking.utils.ImagePicker

class Home() : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getViewModel<HomeViewModelImpl>()
        val uiState = viewModel.collectAsState().value
        HomePageComponent(uiState, viewModel::onEventDispatcher)
    }

    @Composable
    fun HomePageComponent(uiState: HomeUIState, onEventDispatcher: (HomeIntent) -> Unit) {
        val context = LocalContext.current
        var isLoad by remember {
            mutableStateOf(true)
        }
        var list: List<GetCardsResponse> = remember {
            mutableStateListOf()
        }
        when (uiState) {
            is HomeUIState.Loading -> {
                isLoad = uiState.isLoading
                list = uiState.cardList
            }
            is HomeUIState.Success -> {
                isLoad = false
                list = uiState.cardList
            }
            else -> {}
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(text = "My Account", fontWeight = FontWeight.Bold, fontSize = 25.sp)

                CardViewPager(list = list, isLoad = isLoad)
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircleButton(text = "AddCard", icon = R.drawable.shape) {
                    }
                    CircleButton(text = "Pay", icon = R.drawable.pay) {
                    }
                    CircleButton(text = "Send", icon = R.drawable.send) {
                    }
                    CircleButton(text = "History", icon = R.drawable.ic_baseline_history_24) {

                    }

                }
            }


        }

    }

}