package com.himanshu.androidbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.himanshu.androidbasics.ui.theme.AndroidBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBasicsTheme {
                Quotes()
            }
        }
    }
}


@Composable
fun HeaderNavigation(
    navItems: List<AppState.Page>,
    selected: AppState.Page,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(
                color = Color(124, 16, 236, 230),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        navItems.forEachIndexed { index, it ->
            Text(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = if (selected == it) Color.White else Color.LightGray,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .padding(5.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(9.dp))
                    .clickable { onClick(index) },
                text = it.title,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }

}


@Composable
fun Quotes(
    viewModel: MainViewModel = viewModel()
) {
    val appState = viewModel.appState.collectAsStateWithLifecycle()
    val navigation = appState.value.navigation


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(24, 24, 24, 255))
    ) {
        HeaderNavigation(
            navItems = navigation.navItems,
            selected = navigation.selected,
            onClick = { viewModel.selectPage(navigation.navItems[it]) }
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val selected = appState.value.navigation.selected
                val index = appState.value.navigation.navItems.indexOf(selected)
                when (index) {
                    1 -> {
                        QuoteCard(appState.value.quoteOfTheDay)
                    }

                    else -> {}
                }
            }
        }
    }


}


@Composable
fun QuoteCard(quote: AppState.SampleQuote) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.4f)
            .background(
                color = Color(50, 2, 3),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color(124, 16, 236, 230),
            disabledContentColor = Color.White,
            disabledContainerColor = Color(124, 16, 236, 230)
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                text = quote.quote,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black)
                    .padding(10.dp),
                text = "- ${quote.author}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(45.dp)
                    .align(Alignment.BottomStart)
                    .background(shape = CircleShape, color = Color.Black)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    .clip(CircleShape)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "favorite button"
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidBasicsTheme {
        Quotes()
    }
}