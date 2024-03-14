package dev.kobzar.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.onboarding.screens.DetailsScreen
import dev.kobzar.onboarding.screens.FinalScreen
import dev.kobzar.onboarding.screens.WelcomeScreen
import dev.kobzar.preferences.model.DangerNotifyPrefs
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.preferences.model.UserPreferencesModel
import dev.kobzar.ui.compose.components.buttons.text.OnBoardingSkipButton
import dev.kobzar.ui.compose.components.indicators.OnBoardingPageIndicator
import dev.kobzar.ui.compose.theme.AppTheme
import kotlinx.coroutines.launch

class OnBoardingScreen : Screen {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<OnBoardingViewModel>()
        val navigator = LocalNavigator.current
        val asteroidsScreen = rememberScreen(SharedScreen.AsteroidsListScreen)

        val coroutineScope = rememberCoroutineScope()

        val pagerState = rememberPagerState(pageCount = {
            3
        })

        LaunchedEffect(Unit) {
            viewModel.setUserPreferences(
                prefs = UserPreferencesModel(
                    diameterUnits = DiameterUnit.KILOMETER,
                    relativeVelocityUnits = RelativeVelocityUnit.KM_S,
                    missDistanceUnits = MissDistanceUnit.KILOMETER,
                    dangerNotifyPrefs = DangerNotifyPrefs(
                        syncHours = 1,
                        checkIntervalHours = 24
                    )
                )
            )
            viewModel.configureFirstStart()
        }

        var diameterUnit = "Km"
        var velocityUnit = "Km/s"
        var distanceUnit = "Km"

        OnBoardingScreenComposable(
            pagerState = pagerState,
            onFinishClick = {
                navigator?.let {
                    it.push(asteroidsScreen)
                    it.replaceAll(asteroidsScreen)
                }

                coroutineScope.launch {
                    viewModel.setUserPreferences(
                        prefs = UserPreferencesModel(
                            diameterUnits = when (diameterUnit) {
                                "Meter" -> DiameterUnit.METER
                                "Mile" -> DiameterUnit.MILE
                                "Feet" -> DiameterUnit.FEET
                                else -> DiameterUnit.KILOMETER
                            },
                            relativeVelocityUnits = when (velocityUnit) {
                                "Km/h" -> RelativeVelocityUnit.KM_H
                                "Mile/h" -> RelativeVelocityUnit.MILE_H
                                else -> RelativeVelocityUnit.KM_S
                            },
                            missDistanceUnits = when (distanceUnit) {
                                "Mile" -> MissDistanceUnit.MILE
                                "Lunar" -> MissDistanceUnit.LUNAR
                                "Astronomical" -> MissDistanceUnit.ASTRONOMICAL
                                else -> MissDistanceUnit.KILOMETER
                            },
                            dangerNotifyPrefs = DangerNotifyPrefs(
                                syncHours = 1,
                                checkIntervalHours = 24
                            )
                        )
                    )
                    viewModel.configureFirstStart()
                }
            },
            onSkipClick = {
                navigator?.let {
                    it.push(asteroidsScreen)
                    it.replaceAll(asteroidsScreen)
                }
            },
            diameterOnOptionSelected = {
                diameterUnit = it
            },
            velocityOnOptionSelected = {
                velocityUnit = it
            },
            distanceOnOptionSelected = {
                distanceUnit = it
            }
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingScreenComposable(
    pagerState: PagerState,
    onFinishClick: () -> Unit,
    onSkipClick: () -> Unit,
    diameterOnOptionSelected: (String) -> Unit,
    velocityOnOptionSelected: (String) -> Unit,
    distanceOnOptionSelected: (String) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = AppTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppTheme.colors.background,
                            AppTheme.colors.tertiaryViolet200
                        )
                    )
                )
        ) {
            OnBoardingHeader(
                currentPage = pagerState.currentPage,
                onSkipClick = onSkipClick,
                skipVisible = pagerState.currentPage != 2
            )
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> WelcomeScreen(
                        onNextClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        }
                    )

                    1 -> DetailsScreen(
                        parallaxEffect = pagerState.currentPageOffsetFraction
                    ) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }

                    2 -> FinalScreen(
                        parallaxEffect = pagerState.currentPageOffsetFraction,
                        onFinishClick = onFinishClick,
                        diameterOnOptionSelected = diameterOnOptionSelected,
                        velocityOnOptionSelected = velocityOnOptionSelected,
                        distanceOnOptionSelected = distanceOnOptionSelected
                    )
                }
            }
        }
    }
}

@Composable
fun OnBoardingHeader(
    currentPage: Int,
    onSkipClick: () -> Unit,
    skipVisible: Boolean
) {
    Column {
        OnBoardingPageIndicator(currentPage)
        OnBoardingSkipButton(onSkipClick, skipVisible)
    }
}