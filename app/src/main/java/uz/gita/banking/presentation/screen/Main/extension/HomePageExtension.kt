package uz.gita.banking.presentation.screen.Main.extension

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer
import uz.gita.banking.R
import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.ui.theme.AddCardColor
import uz.gita.banking.ui.theme.BankingTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardViewPager(list: List<GetCardsResponse>, isLoad: Boolean) {
    val pagerState = rememberPagerState()
    HorizontalPager(
        pageCount = if (!isLoad) list.size + 1 else list.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 30.dp),
        modifier = if (isLoad) Modifier.shimmer() else Modifier
    ) { page ->
        if (page == list.size) {
            if (!isLoad) AddCartItem(pagerState.currentPage == page) {}
        } else CardItem(list[page], pagerState.currentPage == page, isLoad)
    }
    LaunchedEffect(key1 = pagerState) {
        pagerState.animateScrollToPage(0)
    }
}

@Composable
private fun AddCartItem(isSelected: Boolean, onclick: () -> Unit) {
    val modifier = if (isSelected) {
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1.77f)
            .background(AddCardColor)
            .clickable {
                onclick()
            }
            .padding(40.dp)
    } else {
        Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1.77f)
            .background(AddCardColor)
            .clickable {
                onclick()
            }
            .padding(40.dp)
    }
    Icon(
        tint = Color.White,
        painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
        contentDescription = "add",
        modifier = modifier
    )


}

@Composable
private fun CardItem(item: GetCardsResponse, isSelected: Boolean, isLoad: Boolean) {
    val modifier = if (isSelected) {
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1.77f)
            .shadow(
                30.dp
            )
    } else {
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1.77f)
            .shadow(
                30.dp
            )
    }
    Box(
        modifier = modifier.background(Color.Gray)
    ) {
        if (!isLoad) {
            Image(
                painter = painterResource(
                    id = when (item.themeType) {
                        1 -> {
                            R.drawable.credit1
                        }
                        2 -> {
                            R.drawable.credit2
                        }
                        3 -> {
                            R.drawable.credit3
                        }
                        4 -> {
                            R.drawable.credit4
                        }
                        5 -> {
                            R.drawable.credit5
                        }
                        else -> {
                            R.drawable.credit6
                        }
                    }
                ),
                contentDescription = "back",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                Text(text = "${item.name}")
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        Modifier
                            .weight(3f)
                            .padding(end = 20.dp)
                    ) {
                        Text(text = "****${item.pan}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${item.expiredMonth}/${
                                item.expiredYear.toString().substring(1, 3)
                            }", fontSize = 20.sp
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(0.5f))
                Text(text = "Balance", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = "${'$'} ${item.amount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}

@Composable
fun CardUserInfo() {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)

    ) {
        Text(
            text = "User Info",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
        Row(modifier = Modifier.padding(10.dp)) {
            Image(
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.lighting(Color.White, Color.Black),
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.credit3),
                contentDescription = "userImage"
            )
            Column(modifier = Modifier.padding(start = 15.dp)) {
                Text(
                    text = "First Name",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Last Name: Zayniddinov",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }
    }
}

@Composable
fun CircleButton(text: String, icon: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            shape = CircleShape,
            modifier = Modifier
                .padding(10.dp)
                .shadow(10.dp, CircleShape)
                .clip(CircleShape)
                .size(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            contentPadding = PaddingValues(20.dp),
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "icon",
                tint = Color.Black,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = text, fontSize = 12.sp)
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun prev1() {
    BankingTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            CardUserInfo()
        }
    }
}