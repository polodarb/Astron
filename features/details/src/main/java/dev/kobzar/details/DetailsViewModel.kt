package dev.kobzar.details

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.domain.useCases.reformatUnits.ReformatDiameterUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatMissDistanceUseCase
import dev.kobzar.domain.useCases.reformatUnits.ReformatRelativeVelocityUseCase
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

typealias AsteroidDetails = UiState<MainDetailsModel>

class DetailsViewModel @Inject constructor(
//    private val repository: AsteroidDetailsRepository
//    private val asteroidDetails: FormatAsteroidDetailsByPrefs,
    private val repository: AsteroidDetailsRepository,
    private val datastore: DataStoreRepository,
    private val reformatDiameterUseCase: ReformatDiameterUseCase,
    private val reformatMissDistanceUnitUseCase: ReformatMissDistanceUseCase,
    private val reformatRelativeVelocityUnitUseCase: ReformatRelativeVelocityUseCase
) : ScreenModel {

    private val _details = MutableStateFlow<AsteroidDetails>(UiState.Loading())
    val details: StateFlow<AsteroidDetails> = _details.asStateFlow()

    fun getAsteroidDetails(asteroid: String?) {
        screenModelScope.launch(Dispatchers.IO) {
            if (asteroid != null) {
                repository.getAsteroidDetails(asteroid).collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            val currentDate = LocalDate.now()
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            val closestApproach = uiState.data.closeApproachData
                                .filter { data ->
                                    LocalDate.parse(
                                        data.closeApproachDate,
                                        formatter
                                    ) >= currentDate
                                }
                                .minBy { LocalDate.parse(it.closeApproachDate, formatter) }

                            val reformattedCloseApproachData = closestApproach.copy(
                                relativeVelocity = reformatRelativeVelocityUnitUseCase(
                                    closestApproach.relativeVelocity
                                ),
                                missDistance = reformatMissDistanceUnitUseCase(closestApproach.missDistance)
                            )

                            datastore.getUserPreferences().collect { prefs ->
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