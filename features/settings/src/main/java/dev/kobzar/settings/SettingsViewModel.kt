package dev.kobzar.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.DataStoreRepository
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val prefsRepository: DataStoreRepository
): ScreenModel {

    private val _userPrefs = MutableStateFlow<UiState<UserPreferencesModel?>>(UiState.Success(null))
    val userPrefs: StateFlow<UiState<UserPreferencesModel?>> = _userPrefs.asStateFlow()

    init {
        getUserPrefs()
    }

    private fun getUserPrefs() {
        screenModelScope.launch {
            try {
                prefsRepository.getUserPreferences().collect {
                    _userPrefs.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _userPrefs.value = UiState.Error(e)
            }
        }
    }

    fun updatePrefsData(data: UserPreferencesModel) {
        screenModelScope.launch(Dispatchers.IO) {
            prefsRepository.setUserPreferences(data)
        }
    }

}