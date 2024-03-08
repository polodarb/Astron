package dev.kobzar.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.repository.models.PrefsDetailsModel
import dev.kobzar.repository.uiStates.UiState
import dev.kobzar.ui.compose.components.charts.CompareSizeChart
import dev.kobzar.ui.compose.components.inserts.InsertError
import dev.kobzar.ui.compose.components.inserts.InsertLoader
import dev.kobzar.ui.compose.components.topbars.SecondaryTopBar
import dev.kobzar.ui.compose.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

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
        viewModel.getAsteroidDetails(asteroidId)

        val asteroidData = viewModel.details.collectAsState()

        val navigator = LocalNavigator.current

        val compareScreen = rememberScreen(SharedScreen.CompareScreen)

        DetailsScreenComposable(
            data = asteroidData.value,
            onBackClick = { navigator?.pop() },
            onSaveClick = { },
            onCompareClick = { navigator?.push(compareScreen) }
        )
    }

}

@Composable
private fun DetailsScreenComposable(
    data: UiState<PrefsDetailsModel>,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCompareClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (data) {
            is UiState.Loading -> InsertLoader(text = "Loading asteroid details")
            is UiState.Error -> InsertError()
            is UiState.Success -> {
                DetailsMainContent(
                    data = data.data,
                    missDistance = data.diameterUnit,
                    onBackClick = onBackClick,
                    onSaveClick = onSaveClick,
                    onCompareClick = onCompareClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsMainContent(
    data: PrefsDetailsModel,
    missDistance: DiameterUnit?,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCompareClick: () -> Unit
) {

    val pagerState = rememberPagerState(pageCount = {
        2
    })

    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            SecondaryTopBar(
                title = "Asteroid Details",
                onActionClick = onSaveClick,
                onBackClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            DetailsMainContentHeader(
                name = data.name,
                id = data.id,
                sbdAction = {} // todo: add sbd action
            )

            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) {
                when (it) {
                    0 -> DetailsTable(
                        modifier = Modifier
                            .padding(top = AppTheme.spaces.space32),
                        data = data,
                        diameterUnit = missDistance
                    )

                    1 -> DetailsCompareSizeScreen(
                        modifier = Modifier
                            .padding(
                                top = AppTheme.spaces.space32,
                                start = AppTheme.spaces.space16,
                                end = AppTheme.spaces.space16
                            ),
                        objectName = data.name,
                        objectSize = data.estimatedDiameter.estimatedDiameterMax.toFloat()
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsCompareSizeScreen(
    modifier: Modifier = Modifier,
    objectName: String,
    objectSize: Float
) {
    CompareSizeChart(objectDiameter = objectSize, objectName = objectName, modifier = modifier)
}

@Composable
fun DetailsMainContentHeader(
    modifier: Modifier = Modifier,
    name: String,
    id: String,
    sbdAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = AppTheme.spaces.space36),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = AppTheme.typography.semibold24
        )
        Text(
            text = "ID: $id",
            style = AppTheme.typography.regular14,
            color = AppTheme.colors.secondaryGray600,
            modifier = Modifier.padding(top = AppTheme.spaces.space10)
        )
        SbdChip(onClick = sbdAction, modifier = Modifier.padding(top = AppTheme.spaces.space32))
    }
}

@Composable
private fun SbdChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isHover by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHover) AppTheme.colors.secondaryGray200 else AppTheme.colors.background,
        animationSpec = tween(durationMillis = 200, easing = LinearEasing),
        label = "colorAnim"
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isHover = true
                is PressInteraction.Release -> isHover = false
                is PressInteraction.Cancel -> isHover = false
            }
        }
    }

    Row(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, AppTheme.colors.secondaryGray700, CircleShape)
            .background(
                color = backgroundColor
            )
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .padding(AppTheme.spaces.space4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nasa),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
        )
        Spacer(modifier = Modifier.width(AppTheme.spaces.space4))
        Text(
            text = "Go to SBD",
            style = AppTheme.typography.semibold14,
            modifier = Modifier.padding(
                start = AppTheme.spaces.space4,
                end = AppTheme.spaces.space10
            )
        )
    }
}

@Composable
fun DetailsTable(
    modifier: Modifier = Modifier,
    data: PrefsDetailsModel,
    diameterUnit: DiameterUnit?
) {

    val diameterPrefixUnit = with(data.estimatedDiameter) {
        when (diameterUnit) {
            DiameterUnit.KILOMETER -> "Km"
            DiameterUnit.METER -> "Meters"
            DiameterUnit.MILE -> "Miles"
            DiameterUnit.FEET -> "Feet"
            null -> ""
        }
    }

    OutlineBox(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spaces.space16)
    ) {
        Column {
            DetailsTableItem(
                title = "Dangerous",
                value = data.isDangerous.toString(),
                isDangerItem = true
            )

            DetailsTableItem(
                title = "Min diameter",
                value = "${data.estimatedDiameter.estimatedDiameterMin} $diameterPrefixUnit"
            )

            DetailsTableItem(
                title = "Max diameter",
                value = "${data.estimatedDiameter.estimatedDiameterMax} $diameterPrefixUnit"
            )

            DetailsTableItem(
                title = "Close approach",
                value = data.closeApproachData.closeApproachDateFull
            )

            DetailsTableItem(
                title = "Relative velocity",
                value = data.closeApproachData.relativeVelocity
            )

            DetailsTableItem(
                title = "Miss distance",
                value = data.closeApproachData.missDistance
            )

            DetailsTableItem(
                title = "Orbiting body",
                value = data.closeApproachData.orbitingBody
            )

            DetailsTableItem(
                title = "Is sentry object",
                value = data.isSentryObject.toString(),
                isSentryItem = true
            )
        }
    }
}

@Composable
fun DetailsTableItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    isDangerItem: Boolean = false,
    isSentryItem: Boolean = false
) {

    val dangerTextColor = if (isDangerItem) {
        val result = value.toBoolean()
        if (result) AppTheme.colors.secondaryRed else AppTheme.colors.secondaryGreen
    } else {
        AppTheme.colors.primary
    }

    val dangerOrSentryText = if (isDangerItem || isSentryItem) {
        try {
            val result = value.toBoolean()
            if (result) "Yes" else "No"
        } catch (e: Exception) {
            "-"
        }
    } else {
        value
    }

    val isDangerOrSentry = isDangerItem || isSentryItem

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = AppTheme.typography.medium16
            )
            Text(
                text = if (isDangerOrSentry) dangerOrSentryText else value,
                style = AppTheme.typography.semibold14,
                color = if (isDangerItem) dangerTextColor else AppTheme.colors.primary
            )
        }
    }
    HorizontalDivider(color = AppTheme.colors.secondaryBlue100)
}

@Composable
fun OutlineBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .border(1.dp, AppTheme.colors.border, RoundedCornerShape(12.dp))
            .background(AppTheme.colors.backgroundSurface)
            .padding(horizontal = AppTheme.spaces.space16),
        content = content
    )
}