package dev.kobzar.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import dev.kobzar.onboarding.R
import dev.kobzar.onboarding.screens.shared.OnboardingButtonType
import dev.kobzar.onboarding.screens.shared.OnboardingScreenButtons
import dev.kobzar.onboarding.screens.shared.OnboardingScreenDescription
import dev.kobzar.onboarding.screens.shared.OnboardingScreenImage
import dev.kobzar.ui.compose.components.chips.AsterFilterChip
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
internal fun FinalScreen(
    onFinishClick: () -> Unit,
    diameterOnOptionSelected: (String) -> Unit,
    velocityOnOptionSelected: (String) -> Unit,
    distanceOnOptionSelected: (String) -> Unit
) {
    Column {
        OnboardingScreenImage(
            image = R.drawable.onboarding_img_3,
            heightFraction = 0.3f,
            scaleType = ContentScale.Fit
        )
        OnboardingScreenDescription(
            title = stringResource(R.string.onboarding_page3_title),
            modifier = Modifier.padding(top = AppTheme.spaces.space24)
        )
        FinalScreenAdjustments(
            diameterOnOptionSelected = diameterOnOptionSelected,
            velocityOnOptionSelected = velocityOnOptionSelected,
            distanceOnOptionSelected = distanceOnOptionSelected
        )
        OnboardingScreenButtons(
            onClick = onFinishClick,
            buttonType = OnboardingButtonType.PRIMARY
        )
    }
}

@Composable
private fun FinalScreenAdjustments(
    modifier: Modifier = Modifier,
    diameterOnOptionSelected: (String) -> Unit,
    velocityOnOptionSelected: (String) -> Unit,
    distanceOnOptionSelected: (String) -> Unit
) {

    val scrollableState = rememberScrollState()

//    val itemsList = listOf("Km", "Meter", "Mile", "Feet")
    val itemsList = listOf("Km", "Meter", "Mile", "Feet")
    val (diameterSelectedOption, diameterOnOptionSelectedState) = remember {
        mutableStateOf(
            itemsList[0]
        )
    }

    val velocityUnits = listOf("Km/s", "Km/h", "Mile/h")
    val (velocitySelectedOption, velocityOnOptionSelectedState) = remember {
        mutableStateOf(
            velocityUnits[0]
        )
    }

    val distanceUnits = listOf("Km", "Mile", "Lunar", "Astronomical")
    val (distanceSelectedOption, distanceOnOptionSelectedState) = remember {
        mutableStateOf(
            distanceUnits[0]
        )
    }

    Column(
        modifier = modifier
            .padding(
                start = AppTheme.spaces.space24,
                end = AppTheme.spaces.space24,
                top = AppTheme.spaces.space20
            )
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .verticalScroll(scrollableState),
        horizontalAlignment = Alignment.Start
    ) {
        FinalScreenAdjustmentsRow(
            title = stringResource(R.string.onboarding_prefs_diameter_units_title),
            itemsList = itemsList,
            selectedOption = diameterSelectedOption,
            onOptionSelected = {
                diameterOnOptionSelectedState(it)
                diameterOnOptionSelected(it)
            }
        )

        FinalScreenAdjustmentsRow(
            title = stringResource(R.string.onboarding_prefs_relative_velocity_title),
            itemsList = velocityUnits,
            selectedOption = velocitySelectedOption,
            onOptionSelected = {
                velocityOnOptionSelectedState(it)
                velocityOnOptionSelected(it)
            },
            modifier = Modifier.padding(top = AppTheme.spaces.space28)
        )

        FinalScreenAdjustmentsRow(
            title = stringResource(R.string.onboarding_prefs_distance_units_title),
            itemsList = distanceUnits,
            selectedOption = distanceSelectedOption,
            onOptionSelected = {
                distanceOnOptionSelectedState(it)
                distanceOnOptionSelected(it)
            },
            modifier = Modifier.padding(top = AppTheme.spaces.space28)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FinalScreenAdjustmentsRow(
    modifier: Modifier = Modifier,
    title: String,
    itemsList: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = AppTheme.typography.medium14,
            color = AppTheme.colors.outline
        )
        FlowRow(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space12)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.space12)
        ) {
            itemsList.forEach { text ->
                AsterFilterChip(
                    title = text,
                    selected = (text == selectedOption)
                ) {
                    onOptionSelected(text)
                }
            }
        }
    }
}

