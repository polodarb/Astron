package dev.kobzar.app.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AsteroidDetailsRepository,
    private val prefs: DataStoreRepository
) : ViewModel() {

    private val _prefsWorkerInterval = MutableStateFlow<Long?>(null)
    val prefsWorkerInterval: StateFlow<Long?> = _prefsWorkerInterval.asStateFlow()

    init {
        getUserPreferences()
    }

    fun deleteNotifiedAsteroid(asteroid: String) {
        viewModelScope.launch {
            repository.deleteNotifiedAsteroids(asteroid)
        }
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            _prefsWorkerInterval.value =
                prefs.getUserPreferences().first()?.dangerNotifyPrefs?.syncHours?.toLong()
        }
    }

}