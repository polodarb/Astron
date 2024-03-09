package dev.kobzar.settings

import cafe.adriel.voyager.core.model.ScreenModel
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
//    private val prefsUseCase: ABC
): ScreenModel {


    val fakeUserData = UserPreferencesModel(
        diameterUnits = DiameterUnit.KILOMETER,
        missDistanceUnits = MissDistanceUnit.LUNAR,
        relativeVelocityUnits = RelativeVelocityUnit.KM_H
    )

    val _userPrefs = MutableStateFlow<UiState<UserPreferencesModel>>(UiState.Success(fakeUserData))
    val userPrefs: StateFlow<UiState<UserPreferencesModel>> = _userPrefs.asStateFlow()

    init {
        // todo
    }

    fun getUserPrefs() {
        // todo
    }

    fun updateDiameterUnit(newDiameterUnit: DiameterUnit) {
        // todo
    }

    fun updateMissDistanceUnit(newMissDistanceUnit: MissDistanceUnit) {
       // todo
    }

    fun updateRelativeVelocityUnit(newRelativeVelocityUnit: RelativeVelocityUnit) {
        // todo
    }

}