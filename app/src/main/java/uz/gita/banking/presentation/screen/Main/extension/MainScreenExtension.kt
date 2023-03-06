package uz.gita.banking.presentation.screen.Main.extension

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.valentinilk.shimmer.shimmer
import uz.gita.banking.R
import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.ui.theme.AddCardColor
import uz.gita.banking.ui.theme.BankingTheme
import uz.gita.banking.ui.theme.Blue
import uz.gita.banking.ui.theme.Grey


@Composable
fun RowScope.TabNavigationItem(tab: List<Tab>, selectedTab: Tab, selectedTabChange: (Tab) -> Unit) {
    var tabNavigator = LocalTabNavigator.current
    tabNavigator.current = selectedTab
    tab.forEach {
        BottomNavigationItem(selectedContentColor = Blue,
            unselectedContentColor = AddCardColor,
            selected = tabNavigator.current == tab,
            onClick = {
                tabNavigator.current = it
                selectedTabChange(it)
            },
            icon = {
                val tabColor =
                    if (selectedTab.options.index == it.options.index || selectedTab.options.index.toInt() == 0 && it.options.index.toInt() == 0) Blue else Grey

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = it.icon!!,
                        contentDescription = it.title,
                        modifier = Modifier.size(30.dp),
                        tint = tabColor
                    )
                    Text(text = it.title, color = tabColor, fontSize = 10.sp)
                }

            })
    }

}

@Composable
@Preview(showBackground = true)
fun prev() {
    BankingTheme {

    }
}