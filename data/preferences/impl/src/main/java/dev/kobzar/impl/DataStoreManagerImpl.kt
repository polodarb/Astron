package dev.kobzar.impl

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "app_datastore")

class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreManager {

    private val KEY_USER_PREFERENCES = stringPreferencesKey("user_preferences")

    private val prefKeyIsFirstStart = booleanPreferencesKey(name = "is_first_start")
    override val isFirstStart: Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[prefKeyIsFirstStart] ?: true }

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

    override suspend fun getUserPreferences(): Flow<UserPreferencesModel> {
        return getValue<UserPreferencesModel>(KEY_USER_PREFERENCES).map { preferences ->
            preferences ?: UserPreferencesModel(
                diameterUnits = DiameterUnit.KILOMETER,
                missDistanceUnits = MissDistanceUnit.KILOMETER,
                relativeVelocityUnits = RelativeVelocityUnit.KM_S
            )
        }
    }

    private suspend inline fun <reified T> setValue(key: Preferences.Key<String>, value: T) {
        try {
            context.dataStore.edit { preferences ->
                preferences[key] = Json.encodeToString<T>(value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private inline fun <reified T> getValue(key: Preferences.Key<String>): Flow<T?> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[key]
            json?.let {
                try {
                    Json.decodeFromString<T>(json)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}