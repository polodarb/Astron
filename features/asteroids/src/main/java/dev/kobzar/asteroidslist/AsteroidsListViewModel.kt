package dev.kobzar.asteroidslist

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AsteroidsListViewModel @Inject constructor(
    private val repository: AsteroidsRepository
): ScreenModel {

    init {
        getAsteroids()
    }

    fun getAsteroids() = screenModelScope.launch {
        val data = repository.getAsteroidsByDate("2024-02-01", "2024-02-01")
        Log.d("TAG", "getAsteroids: $data")
    }
}