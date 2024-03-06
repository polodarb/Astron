package dev.kobzar.repository.uiStates

sealed interface UiState<T> {
    class Loading<T> : UiState<T>

    data class Success<T>(
        val data: T,
        val dataFromDB: Boolean = false
    ) : UiState<T>

    data class Error<T>(
        val throwable: Throwable? = null
    ) : UiState<T>
}
