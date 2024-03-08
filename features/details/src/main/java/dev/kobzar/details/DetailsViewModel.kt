package dev.kobzar.details

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.domain.useCases.FormatAsteroidDetailsByPrefs
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias AsteroidDetails = UiState<PrefsDetailsModel>

class DetailsViewModel @Inject constructor(
//    private val repository: AsteroidDetailsRepository
    private val asteroidDetails: FormatAsteroidDetailsByPrefs
) : ScreenModel {

    private val _details = MutableStateFlow<AsteroidDetails>(UiState.Loading())
    val details: StateFlow<AsteroidDetails> = _details

    var asteroid: String? = null

    fun setAsteroidId(id: String?) {
        asteroid = id
    }

    fun getAsteroidDetails(asteroid: String?) {
        screenModelScope.launch(Dispatchers.IO) {
            if (asteroid != null) {
                asteroidDetails.invoke(asteroid).collect {
                    when (it) {
                        is UiState.Success -> {
                            _details.value =
                                UiState.Success(
                                    data = it.data,
                                    diameterUnit = it.diameterUnit
                                )
                        }

                        is UiState.Error -> {
                            _details.value = UiState.Error(it.throwable)
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