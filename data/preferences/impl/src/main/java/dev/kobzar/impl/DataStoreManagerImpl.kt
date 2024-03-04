package dev.kobzar.impl

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.JsonParseException
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "app_datastore")

class DataStoreManagerImpl @Inject constructor(private val context: Context) : DataStoreManager {

    private val KEY_FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    private val KEY_USER_PREFERENCES = stringPreferencesKey("user_preferences")

    override suspend fun setFirstLaunch(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_FIRST_LAUNCH] = value
        }
    }

    override suspend fun getFirstLaunch(): Flow<Boolean?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_FIRST_LAUNCH]
        }
    }

    override suspend fun setUserPreferences(prefs: UserPreferencesModel) {
        setValue(KEY_USER_PREFERENCES, prefs)
    }

    override suspend fun getUserPreferences(): Flow<UserPreferencesModel?> {
        return getValue<UserPreferencesModel>(KEY_USER_PREFERENCES)
    }

    private suspend inline fun <reified T> setValue(key: Preferences.Key<String>, value: T) {
        try {
            val gson = Gson()
            val json = gson.toJson(value)
            context.dataStore.edit { preferences ->
                preferences[key] = json
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend inline fun <reified T> getValue(key: Preferences.Key<String>): Flow<T?> {
        val gson = Gson()
        return context.dataStore.data.map { preferences ->
            val json = preferences[key]
            json?.let {
                try {
                    gson.fromJson(json, T::class.java)
                } catch (e: JsonParseException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}