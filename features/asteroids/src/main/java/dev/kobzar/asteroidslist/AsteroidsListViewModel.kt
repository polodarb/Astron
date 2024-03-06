package dev.kobzar.asteroidslist

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kobzar.asteroidslist.utils.Utils.formatDateFromTimestamp
import dev.kobzar.asteroidslist.utils.Utils.getDaysDifference
import dev.kobzar.impl.paging.AsteroidsPagingSource
import dev.kobzar.repository.AsteroidsRepository
import dev.kobzar.repository.models.MainAsteroidsListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

typealias AsteroidsList = PagingData<MainAsteroidsListItem>

class AsteroidsListViewModel @Inject constructor(
    private val repository: AsteroidsRepository
) : ScreenModel {

    private val _marketCoins = MutableStateFlow<AsteroidsList>(PagingData.empty())
    val marketCoins: StateFlow<AsteroidsList> = _marketCoins

    var firstDate: String = getCurrentDate()
    var secondDate: String = getCurrentDate(7)

    init {
        val dateRange = getDaysDifference(firstDate, secondDate)
        getAsteroids(firstDate, dateRange)
    }

    private fun getAsteroids(startDay: String, dateRangeCount: Int, filterDanger: Boolean = false) {
        screenModelScope.launch {
            try {
                Pager(
                    config = PagingConfig(pageSize = 5),
                    pagingSourceFactory = {
                        AsteroidsPagingSource(
                            repository,
                            startDay,
                            dateRangeCount
                        )
                    }
                )
                    .flow
                    .cachedIn(screenModelScope)
                    .map { pagingData ->
                        if (filterDanger) {
                            pagingData.filter { it.isDangerous }
                        } else {
                            pagingData
                        }
                    }
                    .collect { pagingData ->
                        _marketCoins.value = pagingData
                    }
            } catch (e: Exception) {
                Log.e("AsteroidsListViewModel", e.toString())
            }
        }
    }

    private fun getCurrentDate(addedDays: Long = 0): String {
        var currentDate = LocalDate.now()
        currentDate = currentDate.plusDays(addedDays)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

    fun setNewTimes(firstDate: Long, secondDate: Long, danger: Boolean) {
        this.firstDate = formatDateFromTimestamp(firstDate)
        this.secondDate = formatDateFromTimestamp(secondDate)
        val dateRange = getDaysDifference(this.firstDate, this.secondDate)
        getAsteroids(this.firstDate, dateRange, danger)
    }

}