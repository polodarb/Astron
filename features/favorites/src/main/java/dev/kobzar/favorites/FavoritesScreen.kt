package dev.kobzar.favorites

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.ui.compose.components.info.AsteroidCard
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.theme.AppTheme

class FavoritesScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<FavoritesViewModel>()
        val data = viewModel.asteroids.collectAsState()
        val prefsData = viewModel.prefsData.collectAsState()

        val navigator = LocalNavigator.current

        val context = LocalContext.current

        FavoritesScreenComposable(
            state = data.value,
            userPrefsData = prefsData.value,
            onDetailsClick = {
                navigator?.push(ScreenRegistry.get(SharedScreen.DetailsScreen(it)))
            },
            onDeleteClick = { id, name ->
                viewModel.deleteAsteroid(id)
                Toast.makeText(context, "$name has been deleted", Toast.LENGTH_SHORT).show()
            },
            onBackClick = {
                navigator?.pop()
            }
        )
    }

}

@Composable
private fun FavoritesScreenComposable(
    state: UiState<List<dev.kobzar.model.models.MainDetailsModel>>,
    userPrefsData: UserPreferencesModel?,
    onDetailsClick: (asteroidId: String) -> Unit,
    onDeleteClick: (asteroidId: String, name: String) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            SecondaryTopBar(title = "Favorites", onBackClick = onBackClick)
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            when (state) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppTheme.colors.background),
                        contentAlignment = Alignment.Center
                    ) {
                        InsertLoader(text = "Loading asteroid details")
                    }
                }

                is UiState.Error -> InsertError()

                is UiState.Success -> {

                    val dataList = state.data.toList()

                    if (dataList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = contentPadding.calculateTopPadding()),
                            contentAlignment = Alignment.Center
                        ) {
                            InsertError(customText = stringResource(R.string.insert_error_no_favorites_yet))
                        }
                        return@Box
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(dataList) { item ->
                            val data = item.closeApproachData[0] // The closest date to the current time

                            val diameterValue = when (userPrefsData?.diameterUnits) {
                                DiameterUnit.KILOMETER -> item.estimatedDiameter.kilometers
                                DiameterUnit.METER -> item.estimatedDiameter.meters
                                DiameterUnit.MILE -> item.estimatedDiameter.miles
                                DiameterUnit.FEET -> item.estimatedDiameter.feet
                                else -> null
                            }

                            AsteroidCard(
                                diameterUnits = userPrefsData?.diameterUnits?.unit?.let {
                                    stringResource(
                                        id = it
                                    )
                                } ?: "N/A",
                                name = item.name,
                                isDangerous = item.isDangerous,
                                diameterMin = diameterValue?.estimatedDiameterMin ?: 0.0,
                                diameterMax = diameterValue?.estimatedDiameterMax ?: 0.0,
                                orbitingBody = data.orbitingBody,
                                closeApproach = data.closeApproachDate,
                                onCardClick = {
                                    onDetailsClick(item.id)
                                },
                                onDeleteAsteroid = {
                                    onDeleteClick(item.id, item.name)
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(AppTheme.spaces.space24))
                        }
                    }
                }
            }
        }
    }

}