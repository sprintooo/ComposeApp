package com.himanshu.androidbasics

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _appState = MutableStateFlow(AppState.initial())
    val appState = _appState.asStateFlow()

    fun selectPage(page: AppState.Page) {
        _appState.update {
            return@update it.copy(
                navigation = it.navigation.copy(
                    selected = page
                )
            )
        }
    }


}

data class AppState(
    val navigation: Navigation,
    val quoteOfTheDay: SampleQuote
) {
    companion object {
        fun initial(): AppState {
            val pages = buildList {
                add(Page("All Quotes", Color.Blue))
                add(Page("Daily Quote", Color.Green))
                add(Page("Favorites", Color.Red))
            }
            return AppState(
                navigation = Navigation(
                    navItems = pages,
                    selected = pages[1]
                ),
                quoteOfTheDay = SampleQuote(
                    quote = "The best way to predict the future is to create it.",
                    author = "Peter Drucker"
                )
            )
        }
    }

    data class SampleQuote(val quote: String, val author: String)

    data class Page(val title: String, val color: Color)

    data class Navigation(
        val navItems: List<Page>,
        val selected: Page
    )
}