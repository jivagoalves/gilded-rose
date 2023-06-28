package com.gildedrose.domain.items

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StandardQualityTest {
    @Test
    fun `should be a VO`() {
        assertEquals(StandardQuality.ZERO, StandardQuality.ZERO)
    }

    @Test
    fun `ZERO - should be 0`() {
        assertEquals(StandardQuality.of(0), StandardQuality.ZERO)
    }

    @Test
    fun `FIFTY - should be 50`() {
        assertEquals(StandardQuality.of(50), StandardQuality.FIFTY)
    }

    @Test
    fun `should be represented with q`() {
        assertEquals("13q", StandardQuality.of(13).toString())
    }

    @Test
    fun `minus - should be coerced at least 0`() {
        assertEquals(StandardQuality.ZERO, StandardQuality.ZERO - 1)
        assertEquals(StandardQuality.ZERO, StandardQuality.of(1)!! - 2)
    }

    @Test
    fun `plus - should be coerced at most 50`() {
        assertEquals(StandardQuality.FIFTY, StandardQuality.FIFTY + 1)
        assertEquals(StandardQuality.FIFTY, StandardQuality.of(49)!! + 2)
    }

    @Test
    fun `should be capable of being increased or decreased`() {
        assertEquals(StandardQuality.of(0), StandardQuality.of(1)!! - 1)
        assertEquals(StandardQuality.of(2), StandardQuality.of(1)!! + 1)
    }

    @Test
    fun `its value should be between 0 and 50`() {
        assertNull(StandardQuality.of(-1))
        assertNull(StandardQuality.of(51))
        for (value in 0..50) {
            assertNotNull(StandardQuality.of(value))
        }
    }

    @Nested
    inner class InvalidQualityTest {
        @Test
        fun `should have a description and string`() {
            assertEquals("Quality must be between 0 and 50", StandardQuality.InvalidQuality.description)
            assertEquals("InvalidQuality", StandardQuality.InvalidQuality.toString())
        }
    }
}

class LegendaryQualityTest {
    private val legendaryQuality = LegendaryQuality.of(80)

    @Test
    fun `ZERO - should be 0`() {
        assertEquals(LegendaryQuality.of(0), LegendaryQuality.ZERO)
    }

    @Test
    fun `should be represented with q`() {
        assertEquals("13q", LegendaryQuality.of(13).toString())
    }

    @Test
    fun `should be a value object`() {
        assertEquals(LegendaryQuality.of(1), LegendaryQuality.of(1))
    }

    @Test
    fun `its value should never change`() {
        assertEquals(legendaryQuality, legendaryQuality + 1)
        assertEquals(legendaryQuality, legendaryQuality - 1)
    }
}
