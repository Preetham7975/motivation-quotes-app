package com.motivation.quotes.data.repository

import com.motivation.quotes.data.local.QuoteDao
import com.motivation.quotes.data.local.QuotesData
import com.motivation.quotes.data.model.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao
) {
    fun getAllQuotes(): List<Quote> = QuotesData.quotes

    fun getFavoriteQuotes(): Flow<List<Quote>> = quoteDao.getAllFavorites()

    suspend fun toggleFavorite(quote: Quote) {
        val existing = quoteDao.getFavoriteById(quote.id)
        if (existing != null) {
            quoteDao.deleteFavorite(existing)
        } else {
            quoteDao.insertFavorite(quote.copy(isFavorite = true))
        }
    }

    suspend fun isFavorite(id: Int): Boolean = quoteDao.getFavoriteById(id) != null

    fun getQuoteById(id: Int): Quote? = QuotesData.quotes.find { it.id == id }

    fun getQuotesByCategory(category: String): List<Quote> {
        return if (category == "All") QuotesData.quotes
        else QuotesData.quotes.filter { it.category == category }
    }

    fun getRandomQuote(): Quote = QuotesData.quotes.random()

    fun getDailyQuote(): Quote {
        val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)
        val index = dayOfYear % QuotesData.quotes.size
        return QuotesData.quotes[index]
    }
}
