package dev.kobzar.repository

import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUserPreferences(prefs: UserPreferencesModel)

    suspend fun getUserPreferences(): Flow<UserPreferencesModel?>

}