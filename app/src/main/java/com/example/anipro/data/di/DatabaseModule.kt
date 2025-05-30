package com.example.anipro.data.di

import android.content.Context
import androidx.room.Room
import com.example.anipro.data.database.AnimeDao
import com.example.anipro.data.database.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideTwitDatabase(@ApplicationContext appContext: Context): AnimeDatabase {
        return Room.databaseBuilder(appContext, AnimeDatabase::class.java, "TwitDatabase").build()

    }

    @Provides
    fun provideTwitDao(twitDatabase: AnimeDatabase): AnimeDao {
        return twitDatabase.TwitDao()
    }
}