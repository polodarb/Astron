package dev.kobzar.compare

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.domain.utils.FilterDataByPrefsExtensions.getDiameterRangeByUnit
import dev.kobzar.domain.utils.FilterDataByPrefsExtensions.getMissDistanceUnit
import dev.kobzar.domain.utils.FilterDataByPrefsExtensions.getRelativeVelocity
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.ui.compose.components.chips.TitleValueChip
import dev.kobzar.ui.compose.components.fabs.PrimaryFAB
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.modifiers.animateClickable
import dev.kobzar.ui.compose.theme.AppTheme

class CompareScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<CompareViewModel>()
        val data = viewModel.asteroids.collectAsState()
        val prefsData = viewModel.prefsData.collectAsState()

        val navigator = LocalNavigator.current

        CompareScreenComposable(
            state = data.value,
            userPrefsData = prefsData.value,
            onDetailsClick = {
                navigator?.push(ScreenRegistry.get(SharedScreen.DetailsScreen(it)))
                navigator?.replaceAll(
                    listOf(
                        ScreenRegistry.get(SharedScreen.AsteroidsListScreen),
                        ScreenRegistry.get(SharedScreen.DetailsScreen(it))
                    )
                )
            },
            onBackClick = {
                navigator?.pop()
            },
            onReturnToMain = {
                navigator?.popUntilRoot()
            }
        )
    }

}

@Composable
private fun CompareScreenComposable(
    state: UiState<List<dev.kobzar.model.models.MainDetailsModel>>,
    userPrefsData: UserPreferencesModel?,
    onDetailsClick: (asteroidId: String) -> Unit,
    onBackClick: () -> Unit,
    onReturnToMain: () -> Unit
) {
    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            SecondaryTopBar(title = "Compare asteroids", onBackClick = onBackClick)
        },
        floatingActionButton = {
            PrimaryFAB(
                title = stringResource(R.string.compare_screen_fab_title),
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                iconRes = R.drawable.ic_double_arrow,
            ) {
                onReturnToMain()
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(vertical = contentPadding.calculateTopPadding())
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
                        InsertLoader(text = stringResource(R.string.compare_screen_title_loading))
                    }
                }

                is UiState.Error -> InsertError()

                is UiState.Success -> {

                    ComparePager(
                        data = state.data,
                        prefsData = userPrefsData,
                        onCardClick = onDetailsClick
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComparePager(
    data: List<dev.kobzar.model.models.MainDetailsModel>,
    prefsData: UserPreferencesModel?,
    onCardClick: (asteroidId: String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 12.dp

    val veryLargePageCount = Int.MAX_VALUE

    val moreTwoPages = data.size > 2

    val pagerState = rememberPagerState(
        pageCount = { if (moreTwoPages) veryLargePageCount else 2 },
        initialPage = data.size * 512 - 1
    )

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState, pageSize = PageSize.Fixed(screenWidth / if (moreTwoPages) 3 else 2),
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        val adjustedIndex = page % data.size
        val item = data[adjustedIndex]

        CompareInfoCard(
            item = item,
            prefsData = prefsData,
            onCardClick = { onCardClick(item.id) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CompareInfoCard(
    item: dev.kobzar.model.models.MainDetailsModel,
    prefsData: UserPreferencesModel?,
    onCardClick: () -> Unit
) {
    val closeApproachData = item.closeApproachData[0] // This list contains only one item with most closest date
    val diameters = item.estimatedDiameter.getDiameterRangeByUnit(prefsData?.diameterUnits ?: DiameterUnit.KILOMETER)
    val missDistanceUnit = closeApproachData.getMissDistanceUnit(prefsData)
    val relativeVelocity = closeApproachData.getRelativeVelocity(prefsData)

    val diameterPrefixUnit = prefsData?.diameterUnits?.unit?.let { stringResource(id = it) } ?: "N/A"
    val relativeVelocityPrefix = prefsData?.relativeVelocityUnits?.unit?.let { stringResource(id = it) } ?: "N/A"
    val missDistancePrefix = prefsData?.missDistanceUnits?.unit?.let { stringResource(id = it) } ?: "N/A"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, AppTheme.colors.border, RoundedCornerShape(12.dp))
            .animateClickable(
                onClick = onCardClick
            )
            .padding(AppTheme.spaces.space8),
    ) {
        Text(
            text = item.name,
            color = Color.Black,
            style = AppTheme.typography.semibold14,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .basicMarquee(),
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
        HorizontalDivider(color = AppTheme.colors.secondaryBlue100)

        CompareCardLabelText(
            title = stringResource(R.string.compare_screen_card_title_diameter),
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            title = stringResource(R.string.compare_screen_card_title_diameters_max),
            value = "${diameters.second} $diameterPrefixUnit"
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            title = stringResource(R.string.compare_screen_card_title_diameters_min),
            value = "${diameters.first} $diameterPrefixUnit"
        )

        CompareCardLabelText(
            title = stringResource(R.string.compare_screen_card_title_relative_velocity),
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            value = "$relativeVelocity $relativeVelocityPrefix"
        )

        CompareCardLabelText(
            title = stringResource(R.string.compare_screen_card_title_miss_distance),
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            value = "$missDistanceUnit $missDistancePrefix"
        )

        CompareCardLabelText(
            title = stringResource(R.string.compare_screen_card_title_orbiting_body),
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            value = closeApproachData.orbitingBody
        )

        CompareCardLabelText(
            title = stringResource(R.string.compare_screen_card_title_is_sentry),
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            value = if (item.isSentryObject) "Yes" else "No"
        )

        CompareCardLabelText(
            title = stringResource(R.string.compare_screen_card_title_is_dangerous),
        )
        TitleValueChip(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space4)
                .fillMaxWidth(),
            value = if (item.isDangerous) "Yes" else "No"
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompareCardLabelText(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        style = AppTheme.typography.medium12,
        color = AppTheme.colors.outline,
        maxLines = 1,
        modifier = modifier
            .padding(
                top = AppTheme.spaces.space20
            )
            .basicMarquee()
    )
}