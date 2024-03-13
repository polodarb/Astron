package dev.kobzar.impl.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.kobzar.model.models.MainAsteroidsListItem
import dev.kobzar.repository.AsteroidsRepository

class AsteroidsPagingSource(
    private val repository: AsteroidsRepository,
    private val initdate: String,
    private var dateRangeCount: Int
) : PagingSource<String, MainAsteroidsListItem>() {

    override fun getRefreshKey(state: PagingState<String, MainAsteroidsListItem>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MainAsteroidsListItem> {
        val page = params.key
        return try {

            dateRangeCount--

            val response = if (page == null) {
                repository.getAsteroidsByDate(initdate, initdate)
            } else {
                repository.getAsteroidsByDate(page)
            }

            val formattedResponse = response?.nearEarthObjects?.values?.flatten()

            val nextKey = if (dateRangeCount >= 0) response?.pageKeys?.next?.replace(
                "http://",
                "https://"
            ) else null

            LoadResult.Page(
                data = formattedResponse ?: emptyList(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}