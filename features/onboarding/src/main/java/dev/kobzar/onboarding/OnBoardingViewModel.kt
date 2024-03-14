package dev.kobzar.onboarding

import android.content.Context
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesRepository: DataStoreRepository
) : ScreenModel {

    suspend fun setUserPreferences(prefs: UserPreferencesModel) {
        screenModelScope.launch {
            preferencesRepository.setUserPreferences(prefs)
        }
    }

    suspend fun configureFirstStart() {
        preferencesRepository.setIsFirstStart(value = false)
    }

}