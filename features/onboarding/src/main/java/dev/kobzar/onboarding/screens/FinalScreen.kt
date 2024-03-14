package dev.kobzar.onboarding.screens

import androidx.annotation.StringRes
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
import dev.kobzar.preferences.model.DiameterUnit
import dev.kobzar.preferences.model.MissDistanceUnit
import dev.kobzar.preferences.model.RelativeVelocityUnit
import dev.kobzar.ui.compose.components.chips.AsterFilterChip
import dev.kobzar.ui.compose.theme.AppTheme

@Composable
internal fun FinalScreen(
    parallaxEffect: Float = 0f,
    onFinishClick: () -> Unit,
    diameterOnOptionSelected: (DiameterUnit) -> Unit,
    velocityOnOptionSelected: (RelativeVelocityUnit) -> Unit,
    distanceOnOptionSelected: (MissDistanceUnit) -> Unit
) {
    Column {
        OnboardingScreenImage(
            image = R.drawable.onboarding_img_3,
            heightFraction = 0.3f,
            scaleType = ContentScale.Fit,
            parallaxOffset = parallaxEffect
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
    diameterOnOptionSelected: (DiameterUnit) -> Unit,
    velocityOnOptionSelected: (RelativeVelocityUnit) -> Unit,
    distanceOnOptionSelected: (MissDistanceUnit) -> Unit
) {

    val scrollableState = rememberScrollState()

    val (diameterSelectedOption, diameterOnOptionSelectedState) = remember {
        mutableStateOf(DiameterUnit.entries[0])
    }

    val (velocitySelectedOption, velocityOnOptionSelectedState) = remember {
        mutableStateOf(RelativeVelocityUnit.entries[0])
    }

    val (distanceSelectedOption, distanceOnOptionSelectedState) = remember {
        mutableStateOf(MissDistanceUnit.entries[0])
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
            title = R.string.onboarding_prefs_diameter_units_title,
            options = DiameterUnit.entries.associateWith { it.unit },
            selectedOption = diameterSelectedOption,
            onOptionSelected = {
                diameterOnOptionSelectedState(it)
                diameterOnOptionSelected(it)
            }
        )

        FinalScreenAdjustmentsRow(
            title = R.string.onboarding_prefs_relative_velocity_title,
            options = RelativeVelocityUnit.entries.associateWith { it.unit },
            selectedOption = velocitySelectedOption,
            onOptionSelected = {
                velocityOnOptionSelectedState(it)
                velocityOnOptionSelected(it)
            },
            modifier = Modifier.padding(top = AppTheme.spaces.space28)
        )

        FinalScreenAdjustmentsRow(
            title = R.string.onboarding_prefs_distance_units_title,
            options = MissDistanceUnit.entries.associateWith { it.unit },
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
private fun <T> FinalScreenAdjustmentsRow(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    options: Map<T, Int>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = title),
            style = AppTheme.typography.medium14,
            color = AppTheme.colors.outline
        )
        FlowRow(
            modifier = Modifier
                .padding(top = AppTheme.spaces.space12)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.space12)
        ) {
            options.forEach { option ->
                AsterFilterChip(
                    title = stringResource(id = option.value),
                    selected = (option.key == selectedOption)
                ) {
                    onOptionSelected(option.key)
                }
            }
        }
    }
}

