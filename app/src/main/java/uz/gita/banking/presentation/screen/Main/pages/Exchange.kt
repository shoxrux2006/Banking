package uz.gita.banking.presentation.screen.Main.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.banking.R

class Exchange : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Exchange"
            val icon = painterResource(id = R.drawable.trending_up)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {

    }
}