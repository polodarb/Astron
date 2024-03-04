package dev.kobzar.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobzar.repository.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class OnBoardingViewModel @Inject constructor(
//    private val preferencesRepository: DataStoreRepository
): ScreenModel {

    suspend fun setFirstLaunch(value: Boolean) {
        screenModelScope.launch {
//            preferencesRepository.setFirstLaunch(value)
        }
    }

    suspend fun setUserPreferences() {
        screenModelScope.launch {
//            preferencesRepository.getUserPreferences()
        }
    }

}