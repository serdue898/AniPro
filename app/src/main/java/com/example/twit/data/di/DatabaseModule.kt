package com.example.twit.data.di

import android.content.Context
import androidx.room.Room
import com.example.twit.data.database.TwitDao
import com.example.twit.data.database.TwitDatabase
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
    fun provideTwitDatabase(@ApplicationContext appContext:Context): TwitDatabase {
        return Room.databaseBuilder(appContext, TwitDatabase::class.java,"TwitDatabase").build()

    }
    @Provides
    fun provideTwitDao(twitDatabase: TwitDatabase): TwitDao {
        return twitDatabase.TwitDao()
    }
}