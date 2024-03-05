package dev.kobzar.impl.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.kobzar.repository.AsteroidsRepository
import dev.kobzar.repository.models.MainAsteroidsListItem
import dev.kobzar.repository.models.MainAsteroidsModel
import kotlinx.coroutines.delay

class AsteroidsPagingSource(
    private val repository: AsteroidsRepository,
    private val startDate: String,
    private val endDate: String
) : PagingSource<String, MainAsteroidsListItem>() {

    override fun getRefreshKey(state: PagingState<String, MainAsteroidsListItem>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MainAsteroidsListItem> {
        val page = params.key
        return try {

            val response = if (page == null) {
                repository.getAsteroidsByDate(startDate, endDate)
            } else {
                repository.getAsteroidsByDate(page)
            }

//            delay(3000)

            val formattedResponse = response?.nearEarthObjects?.values?.flatten()

            LoadResult.Page(
                data = formattedResponse ?: emptyList(),
                prevKey = null,
                nextKey = response?.pageKeys?.next?.replace("http://", "https://")
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}