package dev.kobzar.favorites

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias FavoritesData = UiState<List<MainDetailsModel>>

class FavoritesViewModel @Inject constructor(
    private val asteroidsRepository: AsteroidDetailsRepository,
    private val datastore: DataStoreRepository
) : ScreenModel {

    private val _asteroids = MutableStateFlow<FavoritesData>(UiState.Loading())
    val asteroids: StateFlow<FavoritesData> = _asteroids

    private val _prefsData = MutableStateFlow<UserPreferencesModel?>(null)
    val prefsData: StateFlow<UserPreferencesModel?> = _prefsData

    private val currentTime = System.currentTimeMillis()
    init {
        getUserPrefsData()
        getAllAsteroids()
    }

    private fun getAllAsteroids() {
        screenModelScope.launch(Dispatchers.IO) {
            asteroidsRepository.getAllAsteroidDetails().collect { it ->
                when (it) {
                    is UiState.Success -> {

                        val closestApproach = it.data.map { mainDetailsModel ->
                            mainDetailsModel.closeApproachData
                                .filter { data ->
                                    data.epochDateCloseApproach >= currentTime
                                }
                                .minBy { it.closeApproachDate }
                        }

                        _asteroids.value = UiState.Success(it.data.map {
                            it.copy(
                                closeApproachData = closestApproach
                            )
                        })
                    }

                    is UiState.Error -> {
                        _asteroids.value = UiState.Error(it.throwable)
                    }

                    is UiState.Loading -> {
                        _asteroids.value = UiState.Loading()
                    }
                }
            }
        }
    }

    fun deleteAsteroid(asteroidId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            asteroidsRepository.deleteAsteroidDetails(asteroidId)
        }
    }

    private fun getUserPrefsData() {
        screenModelScope.launch {
            datastore.getUserPreferences().collect {
                _prefsData.value = it
            }
        }
    }

}