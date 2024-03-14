package dev.kobzar.preferences

import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    val isFirstStart: Flow<Boolean>

    fun setIsFirstStart(value: Boolean)

    suspend fun setUserPreferences(prefs: UserPreferencesModel)

    suspend fun getUserPreferences(): Flow<UserPreferencesModel?>

}