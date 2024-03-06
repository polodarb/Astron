package dev.kobzar.asteroidslist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.repository.models.MainAsteroidsListItem
import dev.kobzar.ui.compose.components.fabs.PrimaryFAB
import dev.kobzar.ui.compose.components.fabs.SecondaryFAB
import dev.kobzar.ui.compose.components.info.AsteroidCard
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.inserts.InsertNoData
import dev.kobzar.ui.compose.components.topbars.MainTopBar
import dev.kobzar.ui.compose.theme.AppTheme
import dev.kobzar.ui.compose.unitsEnum.EstimatedDiameterEnum

class AsteroidsListScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<AsteroidsListViewModel>()

        LaunchedEffect(Unit) {
            viewModel.getAsteroids("2024-02-05", "2024-02-05")
        }

        val asteroidsData = viewModel.marketCoins.collectAsLazyPagingItems()

        val navigator = LocalNavigator.current

        val context = LocalContext.current

        val detailsScreen = rememberScreen(SharedScreen.DetailsScreen(null))
        val settingsScreen = rememberScreen(SharedScreen.SettingsScreen)
        val favoritesScreen = rememberScreen(SharedScreen.FavoritesScreen)

        AsteroidsListScreenComposable(
            dataState = asteroidsData,
            onFavoritesClick = {
                navigator?.push(favoritesScreen)
            },
            onDetailsClick = {
                navigator?.push(detailsScreen)
            },
            onFilterClick = {
                Toast.makeText(context, "Filter BS", Toast.LENGTH_SHORT).show()
            },
            onSettingsClick = {
                navigator?.push(settingsScreen)
            }
        )

    }

}

@Composable
private fun AsteroidsListScreenComposable(
    dataState: LazyPagingItems<MainAsteroidsListItem>,
    onFavoritesClick: () -> Unit,
    onDetailsClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            MainTopBar {
                onSettingsClick()
            }
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(bottom = AppTheme.spaces.space8)
            ) {
                SecondaryFAB(
                    iconRes = R.drawable.ic_filter,
                    onFabClick = onFilterClick,
                    modifier = Modifier.padding(bottom = AppTheme.spaces.space24)
                )
                PrimaryFAB(
                    title = "Favorites",
                    iconRes = R.drawable.ic_fab_favorites,
                    onFabClick = onFavoritesClick
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(AppTheme.colors.background)
        ) {
            LazyColumn {
                items(dataState.itemCount) { index ->
                    dataState[index]?.let { item ->
                        AsteroidCard(
                            dataType = EstimatedDiameterEnum.KILOMETERS, // TODO: Reimplement after settings feature
                            name = item.name,
                            isDangerous = item.isDangerous,
                            diameterMin = item.estimatedDiameter.kilometers.estimatedDiameterMin,
                            diameterMax = item.estimatedDiameter.kilometers.estimatedDiameterMax,
                            orbitingBody = item.closeApproachData[0].orbitingBody, // TODO: Review before release
                            closeApproach = item.closeApproachData[0].closeApproachDate, // TODO: Review before release
                            onCardClick = onDetailsClick
                        )
                    }
                }
                when {
                    dataState.loadState.refresh is LoadState.Loading -> {
                        item {
                            InsertLoader(
                                modifier = Modifier.fillParentMaxSize(),
                                text = "Loading asteroids"
                            )
                        }
                    }

                    dataState.loadState.refresh is LoadState.Error -> {
                        item {
                            InsertNoData(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .fillParentMaxHeight(0.8f)
                            )
                        }
                    }

                    dataState.loadState.append is LoadState.Loading -> {
                        item {
                            LoadingNextPageItem(
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }

                    dataState.loadState.append is LoadState.Error -> {
                        item {
                            InsertError {

                            }
                            Spacer(modifier = Modifier.height(192.dp))
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun LoadingNextPageItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}