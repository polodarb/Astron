package dev.kobzar.domain.useCases

import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.Flow

interface FormatAsteroidDetailsByPrefs {

    suspend operator fun invoke(asteroid: String): Flow<UiState<PrefsDetailsModel>>

}