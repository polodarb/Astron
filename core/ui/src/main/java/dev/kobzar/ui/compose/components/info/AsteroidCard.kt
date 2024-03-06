package dev.kobzar.ui.compose.components.info

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kobzar.ui.compose.theme.AppTheme
import dev.kobzar.ui.compose.unitsEnum.EstimatedDiameterEnum
import dev.kobzar.ui.compose.utils.UnitUtils

@Composable
fun AsteroidCard(
    modifier: Modifier = Modifier,
    dataType: EstimatedDiameterEnum,
    name: String,
    isDangerous: Boolean,
    diameterMin: Double,
    diameterMax: Double,
    orbitingBody: String,
    closeApproach: String,
    onCardClick: () -> Unit
) {

    val units = when (dataType) {
        EstimatedDiameterEnum.KILOMETERS -> "km"
        EstimatedDiameterEnum.METERS -> "m"
        EstimatedDiameterEnum.MILES -> "mi"
        EstimatedDiameterEnum.FEET -> "ft"
    }

    val reformattedDiameterMin = "${UnitUtils.roundDouble(diameterMin)} $units"
    val reformattedDiameterMax = "${UnitUtils.roundDouble(diameterMax)} $units"

    val diametersData = listOf(
        Pair("Min", reformattedDiameterMin),
        Pair("Max", reformattedDiameterMax)
    )

    val otherData = listOf(
        Pair("Orbiting Body", orbitingBody),
        Pair("Close Approach", closeApproach)
    )

    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.spaces.space16, vertical = AppTheme.spaces.space8)
            .border(1.dp, AppTheme.colors.border, RoundedCornerShape(12.dp))
            .background(AppTheme.colors.backgroundSurface)
    ) {
        MainInfo(name = name, isDangerous = isDangerous)
        SecondaryInfo(
            title = "Diameters",
            data = diametersData,
            modifier = Modifier.padding(horizontal = AppTheme.spaces.space16)
        )
        SecondaryInfo(
            title = "Other",
            data = otherData,
            modifier = Modifier.padding(all = AppTheme.spaces.space16)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SecondaryInfo(
    modifier: Modifier = Modifier,
    title: String,
    data: List<Pair<String, String>>
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = AppTheme.typography.medium12,
            color = AppTheme.colors.outline
        )
        FlowRow(
            modifier = Modifier.padding(top = AppTheme.spaces.space6),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spaces.space8),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spaces.space8)
        ) {
            data.forEach { pair ->
                InfoChip(
                    title = pair.first,
                    value = pair.second
                )
            }
        }
    }
}

@Composable
private fun InfoChip(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(AppTheme.colors.secondaryGray100)
            .padding(horizontal = AppTheme.spaces.space10, vertical = AppTheme.spaces.space6)
    ) {
        Text(
            text = "$title: ",
            style = AppTheme.typography.semibold14
        )
        Text(
            text = value,
            style = AppTheme.typography.regular14,
            modifier = Modifier.padding(start = AppTheme.spaces.space2)
        )
    }
}

@Composable
fun MainInfo(
    name: String,
    isDangerous: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spaces.space16),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = AppTheme.typography.semibold16,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IsDangerous(
            isDangerous = isDangerous
        )
    }
}

@Composable
private fun IsDangerous(
    modifier: Modifier = Modifier,
    isDangerous: Boolean
) {

    val containerColor =
        if (isDangerous) AppTheme.colors.secondaryRed100 else AppTheme.colors.secondaryGreen100

    val textColor =
        if (isDangerous) AppTheme.colors.secondaryRed else AppTheme.colors.secondaryGreen

    val textResult = if (isDangerous) "Yes" else "No"

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(containerColor)
            .padding(horizontal = AppTheme.spaces.space8, vertical = AppTheme.spaces.space10),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Danger: ",
            style = AppTheme.typography.bold14,
            color = textColor
        )
        Text(
            text = textResult,
            style = AppTheme.typography.regular14,
            modifier = Modifier.padding(start = AppTheme.spaces.space2),
            color = textColor
        )
    }
}

@Preview
@Composable
private fun AsteroidCardPreview() {
    AsteroidCard(
        dataType = EstimatedDiameterEnum.KILOMETERS,
        name = "347813 (2002 NP1)",
        isDangerous = true,
        diameterMin = 0.7808272775,
        diameterMax = 1.7459828712,
        orbitingBody = "Earth",
        closeApproach = "2021-08-13",
        onCardClick = { }
    )
}