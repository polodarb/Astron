package dev.kobzar.repository.uiStates

import dev.kobzar.preferences.model.UserPreferencesModel

sealed interface UiState<T> {
    class Loading<T> : UiState<T>

    data class Success<T>(
        val data: T,
        val dataFromDB: Boolean = false,
        val userPrefs: UserPreferencesModel? = null
    ) : UiState<T>

    data class Error<T>(
        val throwable: Throwable? = null
    ) : UiState<T>
}
