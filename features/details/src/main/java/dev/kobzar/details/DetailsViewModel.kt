package dev.kobzar.details

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.kobzar.repository.AsteroidDetailsRepository
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

typealias AsteroidDetails = UiState<MainDetailsModel>

class DetailsViewModel @Inject constructor(
    private val repository: AsteroidDetailsRepository
) : ScreenModel {

    private val _details = MutableStateFlow<AsteroidDetails>(UiState.Loading())
    val details: StateFlow<AsteroidDetails> = _details

    var asteroid: String? = null

    fun log() {
        Log.e("DetailsViewModel", asteroid.toString())
    }

    fun setAsteroidId(id: String?) {
        asteroid = id
    }

    fun getAsteroidDetails(asteroid: String?) {
        screenModelScope.launch(Dispatchers.IO) {
            if (asteroid != null) {
                repository.getAsteroidDetails(asteroid).collect {
                    when (it) {
                        is UiState.Success -> {
                            _details.value = UiState.Success(it.data)
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