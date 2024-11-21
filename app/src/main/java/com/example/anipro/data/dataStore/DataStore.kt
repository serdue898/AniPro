package com.example.anipro.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DATA_STORE_NAME = "settings"
private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
class DataStore @Inject constructor(@ApplicationContext val context: Context){


    suspend fun saveTheme( value: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey("theme")] = value
        }
    }

    suspend fun saveColorTheme(value: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey("color")] = value
        }
    }

    suspend fun saveGridType( value: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey("grid")] = value
        }
    }

    fun getTheme() = context.dataStore.data.map { settings ->
        settings[stringPreferencesKey("theme")] ?: "light"
    }

    fun getColorTheme() = context.dataStore.data.map { settings ->
        settings[stringPreferencesKey("color")] ?: "rojo"
    }

    fun getGridType() = context.dataStore.data.map { settings ->
        settings[stringPreferencesKey("grid")] ?: "4x4"
    }
}