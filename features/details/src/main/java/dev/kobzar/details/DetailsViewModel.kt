package dev.kobzar.details

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.domain.useCases.reformatUnits.ReformatDiameterUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatMissDistanceUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatRelativeVelocityUseCase
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
import dev.kobzar.repository.mappers.DatabaseMapper.toMainDetailsWithCloseApproachData
import dev.kobzar.repository.models.MainDetailsCloseApproachData
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias AsteroidDetails = UiState<MainDetailsModel>

class DetailsViewModel @Inject constructor(
    private val repository: AsteroidDetailsRepository,
    private val datastore: DataStoreRepository,
    private val reformatDiameterUseCase: ReformatDiameterUseCase,
    private val reformatMissDistanceUnitUseCase: ReformatMissDistanceUseCase,
    private val reformatRelativeVelocityUnitUseCase: ReformatRelativeVelocityUseCase
) : ScreenModel {

    private val _details = MutableStateFlow<AsteroidDetails>(UiState.Loading())
    val details: StateFlow<AsteroidDetails> = _details.asStateFlow()

    private val _asteroidExists = MutableStateFlow(false)
    val asteroidExists: StateFlow<Boolean> = _asteroidExists.asStateFlow()

    private val _asteroidsCountInDB = MutableStateFlow(0)
    val asteroidsCountInDB: StateFlow<Int> = _asteroidsCountInDB.asStateFlow()

    private var allCloseApproachData: List<MainDetailsCloseApproachData>? = null

    private val currentTime = System.currentTimeMillis()

    init {
        getItemsCount()
    }

    fun checkIfDetailsExists(asteroidId: String?) {
        screenModelScope.launch(Dispatchers.IO) {
            if (asteroidId != null) {
                repository.isAsteroidDetailsExists(asteroidId).collect {
                    _asteroidExists.value = it
                }
            }
        }
    }

    fun insertOrDeleteAsteroidDetails() {
        val detailsValue = details.value
        val asteroidExistsValue = asteroidExists.value

        if (detailsValue is UiState.Success && allCloseApproachData != null) {
            screenModelScope.launch(Dispatchers.IO) {
                if (asteroidExistsValue) {
                    repository.deleteAsteroidDetails(detailsValue.data.id)
                } else {
                    val mainDetailsWithCloseApproachData =
                        detailsValue.data.copy(closeApproachData = allCloseApproachData ?: emptyList())
                            .toMainDetailsWithCloseApproachData(saveTime = currentTime)
                    repository.insertAsteroidDetails(mainDetailsWithCloseApproachData)
                }
            }
        }
    }

    fun getItemsCount() {
        screenModelScope.launch(Dispatchers.IO) {
            repository.getItemsCount().collect {
                _asteroidsCountInDB.value = it
            }
        }
    }

    fun getAsteroidDetails(asteroid: String?) {
        screenModelScope.launch(Dispatchers.IO) {
            if (asteroid != null) {
                repository.getAsteroidDetails(asteroid).collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {

                            val closestApproach = uiState.data.closeApproachData
                                .filter { data ->
                                    data.epochDateCloseApproach >= currentTime
                                }
                                .minBy { it.closeApproachDate }

                            val reformattedCloseApproachData = closestApproach.copy(
                                relativeVelocity = reformatRelativeVelocityUnitUseCase(
                                    closestApproach.relativeVelocity
                                ),
                                missDistance = reformatMissDistanceUnitUseCase(closestApproach.missDistance)
                            )

                            datastore.getUserPreferences().collect { prefs ->
                                allCloseApproachData = uiState.data.closeApproachData
                                _details.value =
                                    UiState.Success(
                                        data = uiState.data.copy(
                                            closeApproachData = listOf(reformattedCloseApproachData),
                                            estimatedDiameter = reformatDiameterUseCase(uiState.data.estimatedDiameter)
                                        ),
                                        userPrefs = prefs
                                    )
                            }
                        }

                        is UiState.Error -> {
                            _details.value = UiState.Error(uiState.throwable)
                        }

                        is UiState.Loading -> {
                            _details.value = UiState.Loading()
                        }
                    }
                }
            } else {
                Log.e("DetailsViewModel", "asteroid is null")
                _details.value = UiState.Error(NullPointerException("asteroid is null"))
            }
        }
    }
}

//class DetailsViewModel @AssistedInject constructor(
//    @Assisted val asteroidId: String?,
//    private val repository: AsteroidDetailsRepository
//) : ScreenModel {
//
//    @AssistedFactory
//    interface Factory : ScreenModelFactory {
//        fun create(asteroidId: String?): DetailsViewModel
//    }
//
//    init {
//        Log.e("DetailsViewModel", asteroidId.toString())
//    }
//
//}