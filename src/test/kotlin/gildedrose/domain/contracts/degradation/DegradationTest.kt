package gildedrose.domain.contracts.degradation

import gildedrose.domain.Quality
import gildedrose.domain.contracts.degradation.Degradation.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DegradationTest {
    @Nested
    inner class NoneTest {
        @Test
        fun `should always keep the original quality`() {
            assertEquals(
                Quality.ZERO,
                NONE.degrade(Quality.ZERO)
            )

            assertEquals(
                Quality.of(30)!!,
                NONE.degrade(Quality.of(30)!!)
            )
        }
    }

    @Nested
    inner class StandardTest {
        @Test
        fun `should decrease the quality by 1`() {
            assertEquals(
                Quality.of(49)!!,
                STANDARD.degrade(Quality.FIFTY)
            )

            assertEquals(
                Quality.of(48)!!,
                STANDARD.degrade(STANDARD.degrade(Quality.FIFTY))
            )

            assertEquals(
                Quality.ZERO,
                STANDARD.degrade(Quality.ZERO)
            )
        }
    }

    @Nested
    inner class ExpiredTest {
        @Test
        fun `should decrease the quality by 2`() {
            assertEquals(
                Quality.of(48)!!,
                EXPIRED.degrade(Quality.FIFTY)
            )

            assertEquals(
                Quality.of(46)!!,
                EXPIRED.degrade(EXPIRED.degrade(Quality.FIFTY))
            )
        }
        @Test
        fun `should be able to decrease the quality to zero`() {
            assertEquals(
                Quality.ZERO,
                EXPIRED.degrade(Quality.of(1)!!)
            )
        }
    }
}