package dev.kobzar.impl

import dev.kobzar.datasource.DataStoreSource
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreSource
): DataStoreRepository {

    override suspend fun setFirstLaunch(value: Boolean) {
        dataSource.setFirstLaunch(value)
    }

    override suspend fun getFirstLaunch(): Flow<Boolean?> {
        return dataSource.getFirstLaunch()
    }

    override suspend fun setUserPreferences(prefs: UserPreferencesModel) {
        dataSource.setUserPreferences(prefs)
    }

    override suspend fun getUserPreferences(): Flow<UserPreferencesModel?> {
        return dataSource.getUserPreferences()
    }
}