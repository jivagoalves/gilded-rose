package gildedrose.domain

import org.junit.jupiter.api.Assertions.*
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
