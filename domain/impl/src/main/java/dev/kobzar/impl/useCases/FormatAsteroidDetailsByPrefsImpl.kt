package dev.kobzar.impl.useCases

import dev.kobzar.domain.mapper.toPrefsDetailsModel
import dev.kobzar.domain.useCases.FormatAsteroidDetailsByPrefs
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.DataStoreRepository
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FormatAsteroidDetailsByPrefsImpl @Inject constructor(
    private val repository: AsteroidDetailsRepository,
    private val datastore: DataStoreRepository
) : FormatAsteroidDetailsByPrefs {

    override suspend fun invoke(asteroid: String): Flow<UiState<PrefsDetailsModel>> = flow {
        datastore.getUserPreferences().collect { userPrefs ->
            repository.getAsteroidDetails(asteroid).collect {
                when (it) {
                    is UiState.Success -> {
                        val currentDate = LocalDate.now()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val closestApproach = it.data.closeApproachData
                            .filter {
                                LocalDate.parse(
                                    it.closeApproachDate,
                                    formatter
                                ) >= currentDate
                            }
                            .minByOrNull { LocalDate.parse(it.closeApproachDate, formatter) }

                        if (closestApproach != null) {
                            val resultData = it.data.copy(closeApproachData = listOf(closestApproach))
                                .toPrefsDetailsModel(
                                    diameterUnit = userPrefs?.diameterUnits,
                                    closestApproach = closestApproach,
                                    missDistanceUnit = userPrefs?.missDistanceUnits,
                                    relativeVelocityUnit = userPrefs?.relativeVelocityUnits
                                )

                            emit(
                                UiState.Success(
                                    data = resultData,
                                    diameterUnit = userPrefs?.diameterUnits
                                )
                            )

                        } else {
                            emit(UiState.Error(Throwable("No closest approach found")))
                        }
                    }

                    is UiState.Error -> {
                        emit(UiState.Error(it.throwable))
                    }

                    is UiState.Loading -> {
                        emit(UiState.Loading())
                    }
                }
            }
        }
    }
}