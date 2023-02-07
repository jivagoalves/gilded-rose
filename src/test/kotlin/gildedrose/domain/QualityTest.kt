package gildedrose.domain

import gildedrose.domain.items.NQuality
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class QualityTest {
    @Test
    fun `ZERO - should be 0`() {
        assertEquals(Quality.of(0)!!, Quality.ZERO)
    }

    @Test
    fun `FIFTY - should be 50`() {
        assertEquals(Quality.of(50)!!, Quality.FIFTY)
    }

    @Test
    fun `its value should be between 0 and 50`() {
        assertNull(Quality.ZERO - 1)
        assertNull(Quality.FIFTY + 1)
        for (value in 0..50) {
            assertNotNull(Quality.of(value))
        }
    }
}

class NQualityTest {
    @Test
    fun `ZERO - should be 0`() {
        assertEquals(NQuality.Standard(0), NQuality.ZERO)
    }

    @Test
    fun `FIFTY - should be 50`() {
        assertEquals(NQuality.Standard(50), NQuality.FIFTY)
    }

    @Nested
    inner class StandardTest {
        @Test
        fun `should be capable of being increased or decreased`() {
            assertEquals(NQuality.Standard(0), NQuality.Standard(1) - 1)
            assertEquals(NQuality.Standard(2), NQuality.Standard(1) + 1)
        }

        @Test
        fun `its value should be between 0 and 50`() {
            assertNull(NQuality.ZERO - 1)
            assertNull(NQuality.FIFTY + 1)
            for (value in 0..50) {
                assertNotNull(NQuality.Standard.of(value))
            }
        }
    }
    @Nested
    inner class LegendaryTest {
        private val legendaryQuality = NQuality.Legendary.of(80)
        @Test
        fun `should be a value object`() {
            assertEquals(NQuality.Legendary.of(1), NQuality.Legendary.of(1))
        }

        @Test
        fun `its value should never change`() {
            assertEquals(legendaryQuality, legendaryQuality + 1)
            assertEquals(legendaryQuality, legendaryQuality - 1)
        }
    }
}
