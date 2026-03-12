package com.motivation.quotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.motivation.quotes.data.model.Quote

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
