package com.motivation.quotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.motivation.quotes.ui.navigation.QuoteNavGraph
import com.motivation.quotes.ui.theme.MotivationQuotesTheme
import com.motivation.quotes.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()
            MotivationQuotesTheme(darkTheme = uiState.isDarkMode) {
                val navController = rememberNavController()
                QuoteNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
