package dev.kobzar.impl.useCases.reformatUnits

import dev.kobzar.model.models.shared.MissDistanceModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ReformatMissDistanceUseCaseImplTest {

    private val cut = ReformatMissDistanceUseCaseImpl()

    @Test
    fun `GIVEN MissDistanceModel WHEN invoke ReformatMissDistanceUseCaseImpl THEN return expected value`() =
        runTest {
            //given
            val givenValue = MissDistanceModel(
                astronomical = "333.333",
                lunar = "333.333",
                kilometers = "666.666",
                miles = "666.666",
            )
            val expectedValue = MissDistanceModel(
                astronomical = "333.333",
                lunar = "333.333",
                kilometers = "666",
                miles = "666",
            )

            //when
            val actualValue = cut.invoke(givenValue)

            //then
            assertEquals(expectedValue, actualValue)
        }
}