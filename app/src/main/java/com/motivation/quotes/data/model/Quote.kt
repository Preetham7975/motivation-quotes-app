package com.motivation.quotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_quotes")
data class Quote(
    @PrimaryKey
    val id: Int,
    val text: String,
    val author: String,
    val category: String,
    val isFavorite: Boolean = false
)

enum class QuoteCategory(val displayName: String) {
    ALL("All"),
    SUCCESS("Success"),
    LIFE("Life"),
    DREAMS("Dreams"),
    PERSEVERANCE("Perseverance"),
    HAPPINESS("Happiness"),
    WISDOM("Wisdom"),
    ANIME("Anime")
}
