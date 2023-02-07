package gildedrose.domain.contracts.lifecycle

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class LegendaryLifeTest {
    private val legendaryLife = LegendaryLife(LocalDate.now())

    @Test
    fun `should never have to be sold`() {
        assertEquals(LocalDate.MAX, legendaryLife.sellBy)
    }
}