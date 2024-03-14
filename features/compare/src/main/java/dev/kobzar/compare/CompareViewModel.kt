package dev.kobzar.compare

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.domain.useCases.reformatUnits.ReformatDiameterUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatMissDistanceUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatRelativeVelocityUseCase
import dev.kobzar.model.models.MainDetailsModel
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias CompareData = UiState<List<MainDetailsModel>>

class CompareViewModel @Inject constructor(
    private val asteroidsRepository: AsteroidDetailsRepository,
    private val datastore: DataStoreRepository,
    private val reformatDiameterUseCase: ReformatDiameterUseCase,
    private val reformatMissDistanceUnitUseCase: ReformatMissDistanceUseCase,
    private val reformatRelativeVelocityUnitUseCase: ReformatRelativeVelocityUseCase
) : ScreenModel {

    private val _asteroids = MutableStateFlow<CompareData>(UiState.Loading())
    val asteroids: StateFlow<CompareData> = _asteroids

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

                        val updatedData = it.data.map { mainDetailsModel ->
                            val closestApproach = mainDetailsModel.closeApproachData
                                .filter { data ->
                                    data.epochDateCloseApproach >= currentTime
                                }
                                .minByOrNull { it.closeApproachDate }

                            mainDetailsModel.copy(
                                closeApproachData = closestApproach?.let {
                                    listOf(
                                        it.copy(
                                            relativeVelocity = reformatRelativeVelocityUnitUseCase(
                                                closestApproach.relativeVelocity
                                            ),
                                            missDistance = reformatMissDistanceUnitUseCase(
                                                closestApproach.missDistance
                                            )
                                        )
                                    )
                                } ?: emptyList(),
                                estimatedDiameter = reformatDiameterUseCase(mainDetailsModel.estimatedDiameter)
                            )
                        }

                        _asteroids.value = UiState.Success(updatedData)
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

    private fun getUserPrefsData() {
        screenModelScope.launch {
            datastore.getUserPreferences().collect {
                _prefsData.value = it
            }
        }
    }

}