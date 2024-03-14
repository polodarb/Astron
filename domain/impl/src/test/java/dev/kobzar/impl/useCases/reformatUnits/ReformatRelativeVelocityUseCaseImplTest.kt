package dev.kobzar.impl.useCases.reformatUnits

import dev.kobzar.model.models.shared.RelativeVelocityModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ReformatRelativeVelocityUseCaseImplTest {

    private val cut = ReformatRelativeVelocityUseCaseImpl()

    @Test
    fun `GIVEN RelativeVelocityModel WHEN invoke ReformatRelativeVelocityUseCaseImpl THEN return expected value`() =
        runTest {
            //given
            val givenValue = RelativeVelocityModel(
                kilometersPerSecond = "333.333",
                kilometersPerHour = "333.333",
                milesPerHour = "666.666",
            )
            val expectedValue = RelativeVelocityModel(
                kilometersPerSecond = "333",
                kilometersPerHour = "333",
                milesPerHour = "666",
            )

            //when
            val actualValue = cut.invoke(givenValue)

            //then
            assertEquals(expectedValue, actualValue)
        }
}