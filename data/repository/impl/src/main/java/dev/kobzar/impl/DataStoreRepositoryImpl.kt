package dev.kobzar.impl

import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.preferences.source.DataStoreSource
import dev.kobzar.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreSource
) : DataStoreRepository {

    override fun setIsFirstStart(value: Boolean) {
        dataSource.setIsFirstStart(value = value)
    }

    override suspend fun setUserPreferences(prefs: UserPreferencesModel) {
        dataSource.setUserPreferences(prefs)
    }

    override suspend fun getUserPreferences(): Flow<UserPreferencesModel?> {
        return dataSource.getUserPreferences()
    }
}