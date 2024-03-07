package dev.kobzar.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.kobzar.navigation.shared.SharedScreen
import dev.kobzar.repository.models.MainDetailsModel
import dev.kobzar.repository.uiStates.UiState
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
//        viewModel.setAsteroidId(asteroidId)
//        viewModel.log()
        viewModel.getAsteroidDetails(asteroidId)

        val asteroidData = viewModel.details.collectAsState()

        val navigator = LocalNavigator.current

        val compareScreen = rememberScreen(SharedScreen.CompareScreen)

        DetailsScreenComposable(
            data = asteroidData.value,
            onBackClick = { navigator?.pop() },
            onCompareClick = { navigator?.push(compareScreen) }
        )
    }

}

@Composable
private fun DetailsScreenComposable(
    data: UiState<MainDetailsModel>,
    onBackClick: () -> Unit,
    onCompareClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val result = data) {
            is UiState.Loading -> InsertLoader(text = "Loading asteroid details")
            is UiState.Error -> InsertError()
            is UiState.Success -> {
                DetailsMainContent(result.data)
            }
        }
    }
}

@Composable
fun DetailsMainContent(
    data: MainDetailsModel
) {
    Scaffold(
        containerColor = AppTheme.colors.background,
        topBar = {
            SecondaryTopBar(
                title = "Asteroid Details",
                onActionClick = { },
                onBackClick = { }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            LazyColumn {
                item {
                    Text(
                        text = "${data.closeApproachData}",
                        modifier = Modifier.padding(
                            horizontal = AppTheme.spaces.space16,
                            vertical = AppTheme.spaces.space12
                        ),
                        style = AppTheme.typography.semibold16
                    )
                    Spacer(modifier = Modifier.height(AppTheme.spaces.space12))
                }
            }
        }
    }
}