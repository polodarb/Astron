package dev.kobzar.impl

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.JsonParseException
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "app_datastore")

class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreManager {

    private val KEY_USER_PREFERENCES = stringPreferencesKey("user_preferences")

    private val prefKeyIsFirstStart = booleanPreferencesKey(name = "is_first_start")
    override val isFirstStart: Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[prefKeyIsFirstStart] ?: true }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            isFirstStart.collect { Log.e("DEV!", it.toString()) }
        }
    }

    override fun setIsFirstStart(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { prefs ->
                prefs[prefKeyIsFirstStart] = value
            }
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