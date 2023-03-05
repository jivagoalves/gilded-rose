package com.gildedrose.domain.contracts.lifecycle

import com.gildedrose.day
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class LegendaryLifeTest {
    @Test
    fun `should never have to be sold`() {
        val legendaryLife = LegendaryLife(LocalDate.now())
        assertEquals(LocalDate.MAX, legendaryLife.sellBy)
    }

    @Test
    fun `should calculate sell in when not expired`() {
        val legendaryLife = LegendaryLife(LocalDate.MAX.minusDays(1))
        assertEquals(1.day, legendaryLife.sellIn)
    }

    @Test
    fun `should calculate sell in when expired`() {
        val legendaryLife = LegendaryLife(LocalDate.MAX)
        assertEquals(0.day, legendaryLife.sellIn)
    }
}