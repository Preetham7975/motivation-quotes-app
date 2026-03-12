package com.motivation.quotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motivation.quotes.data.model.Quote
import com.motivation.quotes.data.model.QuoteCategory
import com.motivation.quotes.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuoteUiState(
    val currentQuote: Quote? = null,
    val allQuotes: List<Quote> = emptyList(),
    val favoriteQuotes: List<Quote> = emptyList(),
    val selectedCategory: QuoteCategory = QuoteCategory.ALL,
    val isCurrentFavorite: Boolean = false,
    val isDarkMode: Boolean = true,
    val currentBackgroundIndex: Int = 0
)

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuoteUiState())
    val uiState: StateFlow<QuoteUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
        observeFavorites()
    }

    private fun loadInitialData() {
        val allQuotes = repository.getAllQuotes()
        val dailyQuote = repository.getDailyQuote()
        _uiState.update { state ->
            state.copy(
                allQuotes = allQuotes,
                currentQuote = dailyQuote
            )
        }
        checkIfFavorite(dailyQuote.id)
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getFavoriteQuotes().collect { favorites ->
                _uiState.update { state ->
                    state.copy(favoriteQuotes = favorites)
                }
            }
        }
    }

    fun getNextQuote() {
        val filteredQuotes = getFilteredQuotes()
        if (filteredQuotes.isEmpty()) return
        val currentIndex = filteredQuotes.indexOfFirst { it.id == _uiState.value.currentQuote?.id }
        val nextIndex = (currentIndex + 1) % filteredQuotes.size
        val nextQuote = filteredQuotes[nextIndex]
        val nextBgIndex = (_uiState.value.currentBackgroundIndex + 1) % 8
        _uiState.update { state ->
            state.copy(
                currentQuote = nextQuote,
                currentBackgroundIndex = nextBgIndex
            )
        }
        checkIfFavorite(nextQuote.id)
    }

    fun getPreviousQuote() {
        val filteredQuotes = getFilteredQuotes()
        if (filteredQuotes.isEmpty()) return
        val currentIndex = filteredQuotes.indexOfFirst { it.id == _uiState.value.currentQuote?.id }
        val prevIndex = if (currentIndex <= 0) filteredQuotes.size - 1 else currentIndex - 1
        val prevQuote = filteredQuotes[prevIndex]
        val prevBgIndex = if (_uiState.value.currentBackgroundIndex <= 0) 7 else _uiState.value.currentBackgroundIndex - 1
        _uiState.update { state ->
            state.copy(
                currentQuote = prevQuote,
                currentBackgroundIndex = prevBgIndex
            )
        }
        checkIfFavorite(prevQuote.id)
    }

    fun getRandomQuote() {
        val filteredQuotes = getFilteredQuotes()
        if (filteredQuotes.isEmpty()) return
        val randomQuote = filteredQuotes.random()
        val randomBgIndex = (0..7).random()
        _uiState.update { state ->
            state.copy(
                currentQuote = randomQuote,
                currentBackgroundIndex = randomBgIndex
            )
        }
        checkIfFavorite(randomQuote.id)
    }

    fun toggleFavorite() {
        val currentQuote = _uiState.value.currentQuote ?: return
        viewModelScope.launch {
            repository.toggleFavorite(currentQuote)
            val isFav = repository.isFavorite(currentQuote.id)
            _uiState.update { state ->
                state.copy(isCurrentFavorite = isFav)
            }
        }
    }

    fun removeFavorite(quote: Quote) {
        viewModelScope.launch {
            repository.toggleFavorite(quote)
        }
    }

    fun toggleDarkMode() {
        _uiState.update { state ->
            state.copy(isDarkMode = !state.isDarkMode)
        }
    }

    fun setCategory(category: QuoteCategory) {
        val filteredQuotes = if (category == QuoteCategory.ALL) {
            repository.getAllQuotes()
        } else {
            repository.getQuotesByCategory(category.displayName)
        }
        val firstQuote = filteredQuotes.firstOrNull() ?: return
        _uiState.update { state ->
            state.copy(
                selectedCategory = category,
                currentQuote = firstQuote
            )
        }
        checkIfFavorite(firstQuote.id)
    }

    private fun checkIfFavorite(id: Int) {
        viewModelScope.launch {
            val isFav = repository.isFavorite(id)
            _uiState.update { state ->
                state.copy(isCurrentFavorite = isFav)
            }
        }
    }

    private fun getFilteredQuotes(): List<Quote> {
        val category = _uiState.value.selectedCategory
        return if (category == QuoteCategory.ALL) {
            repository.getAllQuotes()
        } else {
            repository.getQuotesByCategory(category.displayName)
        }
    }
}
