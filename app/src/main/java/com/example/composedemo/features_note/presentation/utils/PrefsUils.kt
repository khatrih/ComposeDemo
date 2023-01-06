package com.example.composedemo.features_note.presentation.utils


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TAG = "PrefsUtilsManager"

class PrefsUtils(context: Context) {

    private val applicationContext = context.applicationContext
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    companion object {
        val KEY_AUTH = stringPreferencesKey("key_auth")
        val KEY_TOKEN = intPreferencesKey("key_token")
        //val KEY_AUTH = booleanPreferencesKey("key_auth")
    }

    suspend fun authKey(authKey: String) {
        applicationContext.dataStore.edit {
            it[KEY_AUTH] = authKey
        }
    }

    suspend fun authToken(authToken: Int) {
        applicationContext.dataStore.edit {
            it[KEY_TOKEN] = authToken
        }
    }

    val authToken: Flow<Int?> = applicationContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "exception: $exception")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[KEY_TOKEN] ?: 0
        }

    val authKey: Flow<String?> = applicationContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "exception: $exception")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[KEY_AUTH] ?: ""
        }

    suspend fun clearPref() {
        applicationContext.dataStore.edit {
            it.remove(KEY_AUTH)
            it.remove(KEY_TOKEN)
        }
    }
}
