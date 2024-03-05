package dev.kobzar.asteroidslist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
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
import dev.kobzar.ui.compose.theme.AppTheme

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
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = onFilterClick,
                    modifier = Modifier.padding(bottom = AppTheme.spaces.space20)
                ) {
                    Image(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                }
                ExtendedFloatingActionButton(
                    onClick = onFavoritesClick,
                    text = {
                        Text(text = "Favorites")
                    },
                    icon = {
                        Image(imageVector = Icons.Default.Favorite, contentDescription = null)
                    }
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
//                        AsteroidsListItem(
//                            item = item,
//                            onDetailsClick = onDetailsClick
//                        )

                        Text(text = item.name + "\n" + item.id + "\n" + item.isDangerous + "\n" + item.isSentryObject + "\n" + item.closeApproachData[0].closeApproachDate + "\n"
                        + item.estimatedDiameter.kilometers.estimatedDiameterMax + "\n\n")
                    }
                }
                when {
                    dataState.loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    dataState.loadState.refresh is LoadState.Error -> {
                        val error = dataState.loadState.refresh as LoadState.Error
                        item { Text(text = "Error: ${error.error}") }
                    }

                    dataState.loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier.fillParentMaxSize()) }
                    }

                    dataState.loadState.append is LoadState.Error -> {
                        val error = dataState.loadState.refresh as LoadState.Error
                        item { Text(text = "Error: ${error.error}") }
                    }
                }
            }
        }

    }
}

@Composable
fun PageLoader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fetching data from server",
            color = AppTheme.colors.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CircularProgressIndicator(Modifier.padding(top = 10.dp))
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