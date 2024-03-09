package dev.kobzar.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.details.components.compare.DetailsCompareScreen
import dev.kobzar.details.components.header.DetailsMainContentHeader
import dev.kobzar.details.components.indicators.PageIndicator
import dev.kobzar.details.components.table.DetailsTable
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.ui.compose.components.fabs.PrimaryFAB
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.theme.AppTheme

data class DetailsScreen(
    val asteroidId: String?
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

//        val viewModel = getScreenModel<DetailsViewModel, DetailsViewModel.Factory> { factory ->
//            factory.create(asteroidId = asteroidId)
//        }

        val viewModel = getScreenModel<DetailsViewModel>()

        LaunchedEffect(Unit) {
            viewModel.getAsteroidDetails(asteroidId)
        }

        val asteroidData = viewModel.details.collectAsState()

        val navigator = LocalNavigator.current

        val compareScreen = rememberScreen(SharedScreen.CompareScreen)

        var checkedSavedAsteroid by remember { mutableStateOf(false) }

        DetailsScreenComposable(
            data = asteroidData.value,
            onBackClick = { navigator?.pop() },
            onSaveClick = {
                checkedSavedAsteroid = it
            },
            isSavedAsteroid = checkedSavedAsteroid,
            onCompareClick = { navigator?.push(compareScreen) },
            compareFabVisibility = true, // TODO: Review after favorites feature is implemented
            onSbdClick = {

            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsScreenComposable(
    data: UiState<MainDetailsModel>,
    onBackClick: () -> Unit,
    onSaveClick: (value: Boolean) -> Unit,
    isSavedAsteroid: Boolean,
    onCompareClick: () -> Unit,
    compareFabVisibility: Boolean,
    onSbdClick: (url: String) -> Unit
) {

    val pagerState = rememberPagerState(pageCount = {
        2
    })

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (data) {
            is UiState.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.background), contentAlignment = Alignment.Center) {
                    InsertLoader(text = "Loading asteroid details")
                }
            }
            is UiState.Error -> InsertError()
            is UiState.Success -> {
                DetailsMainContent(
                    data = data.data,
                    pagerState = pagerState,
                    userPrefs = data.userPrefs,
                    onBackClick = onBackClick,
                    onSaveClick = onSaveClick,
                    isSavedAsteroid = isSavedAsteroid,
                    onCompareClick = onCompareClick,
                    compareFabVisibility = compareFabVisibility,
                    onSbdClick = onSbdClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsMainContent(
    data: MainDetailsModel,
    pagerState: PagerState,
    userPrefs: UserPreferencesModel?,
    onBackClick: () -> Unit,
    onSaveClick: (value: Boolean) -> Unit,
    isSavedAsteroid: Boolean,
    onCompareClick: () -> Unit,
    compareFabVisibility: Boolean,
    onSbdClick: (url: String) -> Unit
) {

    val estimatedDiameter = when (userPrefs?.diameterUnits) {
        DiameterUnit.KILOMETER -> data.estimatedDiameter.kilometers.estimatedDiameterMin to data.estimatedDiameter.kilometers.estimatedDiameterMax
        DiameterUnit.METER -> data.estimatedDiameter.meters.estimatedDiameterMin to data.estimatedDiameter.meters.estimatedDiameterMax
        DiameterUnit.MILE -> data.estimatedDiameter.miles.estimatedDiameterMin to data.estimatedDiameter.miles.estimatedDiameterMax
        DiameterUnit.FEET -> data.estimatedDiameter.feet.estimatedDiameterMin to data.estimatedDiameter.feet.estimatedDiameterMax
        else -> null to null
    }

    val closeApproach = data.closeApproachData[0]
    val astronomicalDistance = closeApproach.missDistance.astronomical

    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            SecondaryTopBar(
                title = "Asteroid Details",
                onBackClick = onBackClick,
                onFavoriteClick = onSaveClick,
                isFavorite = isSavedAsteroid
            )
        },
        floatingActionButton = {
            if (compareFabVisibility) {
                PrimaryFAB(
                    title = "Compare",
                    iconRes = R.drawable.ic_compare_arrow,
                    onFabClick = onCompareClick
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            DetailsMainContentHeader(
                name = data.name,
                id = data.id,
                sbdAction = {
                    onSbdClick(data.nasaJplUrl)
                }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.spaces.space32),
                verticalAlignment = Alignment.Top,
            ) {
                when (it) {
                    0 -> DetailsTable(
                        modifier = Modifier,
                        data = data,
                        userPrefs = userPrefs
                    )

                    1 -> DetailsCompareScreen(
                        modifier = Modifier
                            .padding(horizontal = AppTheme.spaces.space16),
                        objectName = data.name,
                        objectSize = estimatedDiameter.second?.toFloat() ?: 0f,
                        astronomicalDistance = astronomicalDistance.toFloat()
                    )
                }
            }

            PageIndicator(
                pagerState = pagerState,
                pageCount = pagerState.pageCount,
                modifier = Modifier
                    .padding(AppTheme.spaces.space16)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}