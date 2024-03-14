package dev.kobzar.app.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobzar.repository.AsteroidDetailsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AsteroidDetailsRepository
) : ViewModel() {

    fun deleteNotifiedAsteroid(asteroid: String) {
        viewModelScope.launch {
            repository.deleteNotifiedAsteroids(asteroid)
        }

    }

}