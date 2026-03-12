package com.motivation.quotes.di

import android.content.Context
import androidx.room.Room
import com.motivation.quotes.data.local.QuoteDao
import com.motivation.quotes.data.local.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuoteDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            "quotes_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(database: QuoteDatabase): QuoteDao {
        return database.quoteDao()
    }
}
