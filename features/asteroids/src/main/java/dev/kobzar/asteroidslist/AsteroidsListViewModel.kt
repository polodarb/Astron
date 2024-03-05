package dev.kobzar.asteroidslist

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.impl.paging.AsteroidsPagingSource
import dev.kobzar.repository.AsteroidsRepository
import dev.kobzar.repository.models.MainAsteroidsListItem
import dev.kobzar.repository.uiStates.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//typealias AsteroidsList = UiState<PagingData<List<MainAsteroidsListItem>>>
typealias AsteroidsList = PagingData<MainAsteroidsListItem>

class AsteroidsListViewModel @Inject constructor(
    private val repository: AsteroidsRepository
): ScreenModel {

    private val _marketCoins = MutableStateFlow<AsteroidsList>(PagingData.empty())
    val marketCoins: StateFlow<AsteroidsList> = _marketCoins

//    init {
//        getAsteroids("2024-02-01", "2024-02-01")
//    }

    fun getAsteroids(startDay: String, endDay: String) {
        screenModelScope.launch {
            try {
                Pager(
                    config = PagingConfig(pageSize = 5),
                    pagingSourceFactory = { AsteroidsPagingSource(repository, startDay, endDay) }
                ).flow
                    .collect { pagingData ->
                        _marketCoins.value = pagingData
                    }
            } catch (e: Exception) {
//                _marketCoins.value =
            }
        }
    }

//    fun getAsteroids() = screenModelScope.launch {
//        val data = repository.getAsteroidsByDate("2024-02-01", "2024-02-01")
//        Log.d("TAG", "getAsteroids: $data")
//    }
}