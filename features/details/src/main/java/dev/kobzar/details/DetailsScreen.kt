package dev.kobzar.details

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
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
import dev.kobzar.platform.base.BaseActivity
import dev.kobzar.platform.utils.ConvertDiameterToKm
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.ui.compose.components.fabs.PrimaryFAB
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.theme.AppTheme
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import dev.shreyaspatil.permissionflow.compose.rememberPermissionState
import kotlinx.coroutines.launch

data class DetailsScreen(
    val asteroidId: String?,
    val isDistanceDanger: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun Content() {

//        val viewModel = getScreenModel<DetailsViewModel, DetailsViewModel.Factory> { factory ->
//            factory.create(asteroidId = asteroidId)
//        }

        val viewModel = getScreenModel<DetailsViewModel>()

        LaunchedEffect(Unit) {
            viewModel.getAsteroidDetails(asteroidId)
            viewModel.checkIfDetailsExists(asteroidId)
        }

        val asteroidData = viewModel.details.collectAsState()
        val asteroidExistsData = viewModel.asteroidExists.collectAsState()
        val asteroidsCountInDB = viewModel.asteroidsCountInDB.collectAsState()

        val navigator = LocalNavigator.current

        val context = LocalContext.current

        val compareScreen = rememberScreen(SharedScreen.CompareScreen)

        val uriHandler = LocalUriHandler.current

        val permissionLauncher = rememberPermissionFlowRequestLauncher()
        val state by rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

        DetailsScreenComposable(
            data = asteroidData.value,
            onBackClick = { navigator?.pop() },
            onSaveClick = {
                viewModel.insertOrDeleteAsteroidDetails()
            },
            isSavedAsteroid = asteroidExistsData.value,
            onCompareClick = {
                if (asteroidsCountInDB.value == 1) {
                    if (state.isGranted) {
                        (context as BaseActivity).sendNotification(
                            title = context.getString(R.string.notification_title_compare_error),
                            autoCancel = true
                        )
                    } else if (state.isRationaleRequired == true) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_details_compare_error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_details_compare_error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    navigator?.push(compareScreen)
                }
            },
            compareFabVisibility = asteroidExistsData.value,
            onSbdClick = {
                uriHandler.openUri(it)
            },
            isDistanceDanger = isDistanceDanger
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsScreenComposable(
    data: UiState<dev.kobzar.model.models.MainDetailsModel>,
    onBackClick: () -> Unit,
    onSaveClick: (value: Boolean) -> Unit,
    isSavedAsteroid: Boolean,
    onCompareClick: () -> Unit,
    compareFabVisibility: Boolean,
    onSbdClick: (url: String) -> Unit,
    isDistanceDanger: Boolean
) {

    val pagerState = rememberPagerState(
        pageCount = {
            2
        },
        initialPage = if (isDistanceDanger) 1 else 0
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (data) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background), contentAlignment = Alignment.Center
                ) {
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
                    onSbdClick = onSbdClick,
                    isDistanceDanger = isDistanceDanger
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsMainContent(
    data: dev.kobzar.model.models.MainDetailsModel,
    pagerState: PagerState,
    userPrefs: UserPreferencesModel?,
    onBackClick: () -> Unit,
    onSaveClick: (value: Boolean) -> Unit,
    isSavedAsteroid: Boolean,
    onCompareClick: () -> Unit,
    compareFabVisibility: Boolean,
    onSbdClick: (url: String) -> Unit,
    isDistanceDanger: Boolean
) {

    val comparePagerState =
        rememberPagerState(
            pageCount = { 2 },
            initialPage = if (isDistanceDanger) 1 else 0
        )
    var selectedIndex by remember { mutableIntStateOf(if (isDistanceDanger) 1 else 0) }

    val coroutineScope = rememberCoroutineScope()

    val closeApproach = data.closeApproachData[0] // This list contains only one item with most closest date
    val astronomicalDistance = closeApproach.missDistance.astronomical

    val convertedDiameterToKm = when (userPrefs?.diameterUnits) {
        DiameterUnit.KILOMETER -> data.estimatedDiameter.kilometers.estimatedDiameterMax.toFloat()
        DiameterUnit.METER -> ConvertDiameterToKm.metersToKilometers(data.estimatedDiameter.meters.estimatedDiameterMax.toFloat())
        DiameterUnit.MILE -> ConvertDiameterToKm.milesToKilometers(data.estimatedDiameter.miles.estimatedDiameterMax.toFloat())
        DiameterUnit.FEET -> ConvertDiameterToKm.feetToKilometers(data.estimatedDiameter.feet.estimatedDiameterMax.toFloat())
        else -> 0f
    }

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
            AnimatedVisibility(
                visible = compareFabVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PrimaryFAB(
                    title = "Compare",
                    iconRes = R.drawable.ic_compare_arrow,
                    onFabClick = onCompareClick,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
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
                    .animateContentSize(
                        animationSpec = tween(200, easing = FastOutSlowInEasing)
                    )
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
                        objectSize = convertedDiameterToKm,
                        astronomicalDistance = astronomicalDistance.toFloat(),
                        comparePagerState = comparePagerState,
                        switchIndexPosition = selectedIndex,
                        onSwitchIndexPosition = {
                            selectedIndex = it
                            coroutineScope.launch {
                                comparePagerState.animateScrollToPage(it)
                            }
                        }
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