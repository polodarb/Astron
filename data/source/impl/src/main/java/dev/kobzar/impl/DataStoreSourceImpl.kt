package dev.kobzar.impl

import dev.kobzar.datasource.DataStoreSource
import dev.kobzar.preferences.DataStoreManager
import dev.kobzar.preferences.model.UserPreferencesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : DataStoreSource {

    override suspend fun setFirstLaunch(value: Boolean) {
        withContext(Dispatchers.IO) {
            dataStoreManager.setFirstLaunch(value)
        }
    }

    override suspend fun getFirstLaunch(): Flow<Boolean?> {
        return withContext(Dispatchers.IO) {
            dataStoreManager.getFirstLaunch()
        }
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