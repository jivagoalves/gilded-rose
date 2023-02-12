package gildedrose.domain.quality

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StandardTest {
    @Test
    fun `ZERO - should be 0`() {
        assertEquals(Standard(0), Standard.ZERO)
    }

    @Test
    fun `FIFTY - should be 50`() {
        assertEquals(Standard(50), Standard.FIFTY)
    }

    @Test
    fun `should be represented with q`() {
        assertEquals("13q", Standard.of(13).toString())
    }

    @Test
    fun `minus - should be coerced at least 0`() {
        assertEquals(Standard.ZERO, Standard.ZERO - 1)
        assertEquals(Standard.ZERO, Standard.of(1)!! - 2)
    }

    @Test
    fun `plus - should be coerced at most 50`() {
        assertEquals(Standard.FIFTY, Standard.FIFTY + 1)
        assertEquals(Standard.FIFTY, Standard.of(49)!! + 2)
    }

    @Test
    fun `should be capable of being increased or decreased`() {
        assertEquals(Standard(0), Standard(1) - 1)
        assertEquals(Standard(2), Standard(1) + 1)
    }

    @Test
    fun `its value should be between 0 and 50`() {
        assertNull(Standard.of(-1))
        assertNull(Standard.of(51))
        for (value in 0..50) {
            assertNotNull(Standard.of(value))
        }
    }
}

class LegendaryTest {
    private val legendary = Legendary.of(80)

    @Test
    fun `should be represented with q`() {
        assertEquals("13q", Legendary.of(13).toString())
    }

    @Test
    fun `should be a value object`() {
        assertEquals(Legendary.of(1), Legendary.of(1))
    }

    @Test
    fun `its value should never change`() {
        assertEquals(legendary, legendary + 1)
        assertEquals(legendary, legendary - 1)
    }
}
