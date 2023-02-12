package gildedrose.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class QualityTest {
    @Test
    fun `ZERO - should be 0`() {
        assertEquals(Quality.Standard(0), Quality.ZERO)
    }

    @Test
    fun `FIFTY - should be 50`() {
        assertEquals(Quality.Standard(50), Quality.FIFTY)
    }

    @Nested
    inner class StandardTest {
        @Test
        fun `should be represented with q`() {
            assertEquals("13q", Quality.Standard.of(13).toString())
        }

        @Test
        fun `minus - should be coerced at least 0`() {
            assertEquals(Quality.ZERO, Quality.ZERO - 1)
            assertEquals(Quality.ZERO, Quality.Standard.of(1)!! - 2)
        }

        @Test
        fun `plus - should be coerced at most 50`() {
            assertEquals(Quality.FIFTY, Quality.FIFTY + 1)
            assertEquals(Quality.FIFTY, Quality.Standard.of(49)!! + 2)
        }

        @Test
        fun `should be capable of being increased or decreased`() {
            assertEquals(Quality.Standard(0), Quality.Standard(1) - 1)
            assertEquals(Quality.Standard(2), Quality.Standard(1) + 1)
        }

        @Test
        fun `its value should be between 0 and 50`() {
            assertNull(Quality.Standard.of(-1))
            assertNull(Quality.Standard.of(51))
            for (value in 0..50) {
                assertNotNull(Quality.Standard.of(value))
            }
        }
    }
    @Nested
    inner class LegendaryTest {
        private val legendaryQuality = Quality.Legendary.of(80)

        @Test
        fun `should be represented with q`() {
            assertEquals("13q", Quality.Legendary.of(13).toString())
        }

        @Test
        fun `should be a value object`() {
            assertEquals(Quality.Legendary.of(1), Quality.Legendary.of(1))
        }

        @Test
        fun `its value should never change`() {
            assertEquals(legendaryQuality, legendaryQuality + 1)
            assertEquals(legendaryQuality, legendaryQuality - 1)
        }
    }
}
