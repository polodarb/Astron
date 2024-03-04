package dev.kobzar.datasource

import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.flow.Flow

interface DataStoreSource {

    suspend fun setUserPreferences(prefs: UserPreferencesModel)

    suspend fun getUserPreferences(): Flow<UserPreferencesModel?>

}