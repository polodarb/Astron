package dev.kobzar.impl.useCases.reformatUnits

import dev.kobzar.model.models.shared.MainAsteroidsDiameter
import dev.kobzar.model.models.shared.MainAsteroidsEstimatedDiameter
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ReformatDiameterUseCaseImplTest {

    private val cut = ReformatDiameterUseCaseImpl()

    @Test
    fun `GIVEN MainAsteroidsEstimatedDiameter WHEN invoke ReformatDiameterUseCaseImpl THEN return expected value`() =
        runTest {
            //given
            val givenValue = MainAsteroidsEstimatedDiameter(
                kilometers = MainAsteroidsDiameter(
                    estimatedDiameterMin = 333.33333333,
                    estimatedDiameterMax = 333.33333333,
                ),
                meters = MainAsteroidsDiameter(
                    estimatedDiameterMin = 333.33333333,
                    estimatedDiameterMax = 333.33333333,
                ),
                miles = MainAsteroidsDiameter(
                    estimatedDiameterMin = 666.66666666,
                    estimatedDiameterMax = 666.66666666,
                ),
                feet = MainAsteroidsDiameter(
                    estimatedDiameterMin = 666.66666666,
                    estimatedDiameterMax = 666.66666666,
                ),
            )
            val expectedValue = MainAsteroidsEstimatedDiameter(
                kilometers = MainAsteroidsDiameter(
                    estimatedDiameterMin = 333.333,
                    estimatedDiameterMax = 333.333,
                ),
                meters = MainAsteroidsDiameter(
                    estimatedDiameterMin = 333.333,
                    estimatedDiameterMax = 333.333,
                ),
                miles = MainAsteroidsDiameter(
                    estimatedDiameterMin = 666.667,
                    estimatedDiameterMax = 666.667,
                ),
                feet = MainAsteroidsDiameter(
                    estimatedDiameterMin = 666.667,
                    estimatedDiameterMax = 666.667,
                ),
            )

            //when
            val actualValue = cut.invoke(givenValue)

            //then
            assertEquals(expectedValue, actualValue)

        }
}