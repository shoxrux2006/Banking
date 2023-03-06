package uz.gita.banking.presentation.screen.Main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.banking.presentation.screen.Main.extension.TabNavigationItem
import uz.gita.banking.presentation.screen.Main.pages.Exchange
import uz.gita.banking.presentation.screen.Main.pages.Home
import uz.gita.banking.presentation.screen.Main.pages.Settings
import uz.gita.banking.presentation.screen.Main.pages.Transfer
import uz.gita.banking.presentation.screen.Main.vm.MainIntent
import uz.gita.banking.presentation.screen.Main.vm.MainUIState
import uz.gita.banking.presentation.screen.Main.vm.MainViewModel
import uz.gita.banking.presentation.screen.Main.vm.impl.MainViewModelImpl
import uz.gita.banking.ui.theme.BankingTheme

class MainScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: MainViewModel = getViewModel<MainViewModelImpl>()


        val uiState = viewModel.collectAsState().value
        MainScreenComponent(uiState, viewModel::onEventDispatcher)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreenComponent(uiState: MainUIState, onEventDispatcher: (MainIntent) -> Unit) {
        var currentTab: Tab by remember {
            mutableStateOf(Home())
        }
        when (uiState) {
            is MainUIState.Default -> {
                currentTab = uiState.currentTab
            }
        }
        val tabList = listOf(
            Home(),
            Transfer(),
            Exchange(),
            Settings()
        )
        TabNavigator(Home()) {
            Scaffold(
                content = { CurrentTab() },
                bottomBar = {
                    BottomNavigation(
                        modifier =Modifier,
                        backgroundColor = Color.White
                    ) {

                        TabNavigationItem(
                            tab = tabList,
                            selectedTab = currentTab,)
                            { onEventDispatcher(MainIntent.Navigation(it)) }
                    }
                }


            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun prev() {
        BankingTheme {
        }
    }

}