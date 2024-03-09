package dev.kobzar.impl.source

import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.preferences.source.DataStoreSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : DataStoreSource {

    override fun setIsFirstStart(value: Boolean) {
        dataStoreManager.setIsFirstStart(value = value)
    }

    override suspend fun setUserPreferences(prefs: UserPreferencesModel) {
        withContext(Dispatchers.IO) {
            dataStoreManager.setUserPreferences(prefs)
        }
    }

    override suspend fun getUserPreferences(): Flow<UserPreferencesModel?> {
        return withContext(Dispatchers.IO) {
            dataStoreManager.getUserPreferences()
        }
    }
}