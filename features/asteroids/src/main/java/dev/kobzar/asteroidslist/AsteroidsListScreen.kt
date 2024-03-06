package dev.kobzar.asteroidslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.asteroidslist.bottomSheet.FilterBottomSheet
import dev.kobzar.asteroidslist.utils.Utils.formatDateFromTimestamp
import dev.kobzar.asteroidslist.utils.toMillis
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
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale

class AsteroidsListScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val viewModel = getScreenModel<AsteroidsListViewModel>()
        val asteroidsData = viewModel.marketCoins.collectAsLazyPagingItems()

        val navigator = LocalNavigator.current

        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        val dateTime = LocalDateTime.now()
        val dateRangePickerState = remember {
            DateRangePickerState(
                initialSelectedStartDateMillis = dateTime.toMillis(),
                initialDisplayedMonthMillis = null,
                initialSelectedEndDateMillis = dateTime.plusDays(7).toMillis(),
                initialDisplayMode = DisplayMode.Picker,
                locale = Locale.getDefault()
            )
        }

        val coroutineScope = rememberCoroutineScope()

        val showDatePicker = remember { mutableStateOf(false) }

        val detailsScreen = rememberScreen(SharedScreen.DetailsScreen(null))
        val settingsScreen = rememberScreen(SharedScreen.SettingsScreen)
        val favoritesScreen = rememberScreen(SharedScreen.FavoritesScreen)

        val firstDateState = remember { mutableStateOf(viewModel.firstDate) }
        val secondDateState = remember { mutableStateOf(viewModel.secondDate) }

        AsteroidsListScreenComposable(
            dataState = asteroidsData,
            onFavoritesClick = {
                navigator?.push(favoritesScreen)
            },
            onDetailsClick = {
                navigator?.push(detailsScreen)
            },
            onFilterClick = {
                showBottomSheet = true
            },
            onSettingsClick = {
                navigator?.push(settingsScreen)
            },
            onFilterBsDismiss = {
                showBottomSheet = false
            },
            sheetFirstDate = firstDateState.value,
            sheetSecondDate = secondDateState.value,
            onSheetSaveClick = { dangerous ->
                val startTS = dateRangePickerState.selectedStartDateMillis
                val endTS = dateRangePickerState.selectedEndDateMillis
                if (startTS != null && endTS != null) {
                    viewModel.setNewTimes(
                        startTS, endTS, dangerous
                    )
                }
                coroutineScope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                }
            },
            sheetState = sheetState,
            onSheetChooseDateClick = {
                showDatePicker.value = true
            },
            showBottomSheet = showBottomSheet
        )

        BsDateRangePicker(
            showDatePicker = showDatePicker.value,
            onDismiss = {
                showDatePicker.value = false
            },
            onConfirmClick = { start, end ->
                firstDateState.value = formatDateFromTimestamp(start)
                secondDateState.value = formatDateFromTimestamp(end)
                showDatePicker.value = false
            },
            state = dateRangePickerState
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AsteroidsListScreenComposable(
    dataState: LazyPagingItems<MainAsteroidsListItem>,
    onFavoritesClick: () -> Unit,
    onDetailsClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onFilterBsDismiss: () -> Unit,
    sheetState: SheetState,
    sheetFirstDate: String?,
    sheetSecondDate: String?,
    onSheetSaveClick: (dangerous: Boolean) -> Unit,
    onSheetChooseDateClick: () -> Unit,
    showBottomSheet: Boolean
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
                item {
                    Spacer(modifier = Modifier.height(AppTheme.spaces.space16))
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
                                dataState.retry()
                            }
                            Spacer(modifier = Modifier.height(192.dp))
                        }
                    }
                }
            }

            FilterBottomSheet(
                showBottomSheet = showBottomSheet,
                sheetState = sheetState,
                onDismiss = onFilterBsDismiss,
                firstDate = sheetFirstDate,
                secondDate = sheetSecondDate,
                onChooseDateClick = onSheetChooseDateClick,
                onSaveClick = onSheetSaveClick,
            )

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BsDateRangePicker(
    showDatePicker: Boolean,
    onDismiss: () -> Unit,
    onConfirmClick: (startDate: Long, endDate: Long) -> Unit,
    state: DateRangePickerState
) {

    if (showDatePicker) {
        val confirmEnabled = remember {
            derivedStateOf {
                state.selectedStartDateMillis?.let { startDate ->
                    state.selectedEndDateMillis?.let { endDate ->
                        endDate - startDate <= 86400000 * 7
                    } ?: false
                } ?: false
            }
        }

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        state.selectedStartDateMillis?.let { startDate ->
                            state.selectedEndDateMillis?.let { endDate ->
                                onConfirmClick(startDate, endDate)
                            }
                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            },
        ) {
            DateRangePicker(
                state = state,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = AppTheme.spaces.space16),
                showModeToggle = false,
            )
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