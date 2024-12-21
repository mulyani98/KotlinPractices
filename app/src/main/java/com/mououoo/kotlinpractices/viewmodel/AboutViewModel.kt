package com.mououoo.kotlinpractices.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AboutViewModel : ViewModel() {
    companion object {
        private const val  HOME_PAGE_URL = "https://mulyani98.github.io/Home-page-Kotlin-Practices/"
        private const val  TERM_OF_USE_URL = "https://mulyani98.github.io/ToU-Kotlin-Practices/"
        private const val  PRIVACY_STATEMENT_URL = "https://mulyani98.github.io/PSA-Kotlin-Practices/"
    }

    private val _menuItems = MutableStateFlow(
        listOf(
            AboutItemData("Home Page", HOME_PAGE_URL),
            AboutItemData("Term of Use", TERM_OF_USE_URL),
            AboutItemData("Privacy Statement", PRIVACY_STATEMENT_URL )
        )
    )
    val menuItems: StateFlow<List<AboutItemData>> = _menuItems
}

data class AboutItemData(val title: String, val url: String)