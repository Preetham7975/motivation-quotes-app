package com.motivation.quotes.data.local

import androidx.room.*
import com.motivation.quotes.data.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM favorite_quotes")
    fun getAllFavorites(): Flow<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(quote: Quote)

    @Delete
    suspend fun deleteFavorite(quote: Quote)

    @Query("SELECT * FROM favorite_quotes WHERE id = :id")
    suspend fun getFavoriteById(id: Int): Quote?
}
