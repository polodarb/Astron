package dev.kobzar.preferences

import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    suspend fun setUserPreferences(prefs: UserPreferencesModel)

    suspend fun getUserPreferences(): Flow<UserPreferencesModel?>

}