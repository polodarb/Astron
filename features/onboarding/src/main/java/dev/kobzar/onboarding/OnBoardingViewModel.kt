package dev.kobzar.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.domain.useCases.ConfigureFirstStart
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val preferencesRepository: DataStoreRepository,
    private val configureUseCase: ConfigureFirstStart
) : ScreenModel {

    suspend fun setUserPreferences(prefs: UserPreferencesModel) {
        screenModelScope.launch {
            preferencesRepository.setUserPreferences(prefs)
        }
    }

    suspend fun configureFirstStart() {
        screenModelScope.launch {
            configureUseCase()
        }
    }

}