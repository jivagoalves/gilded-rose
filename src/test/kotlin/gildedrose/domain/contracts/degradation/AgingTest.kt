package gildedrose.domain.contracts.degradation

import gildedrose.domain.Quality
import gildedrose.domain.contracts.degradation.Aging.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AgingTest {
    @Nested
    inner class NoneTest {
        @Test
        fun `should always keep the original quality`() {
            assertEquals(
                Quality.ZERO,
                NONE.age(Quality.ZERO)
            )

            assertEquals(
                Quality.Standard.of(30)!!,
                NONE.age(Quality.Standard.of(30)!!)
            )
        }
    }

    @Nested
    inner class StandardTest {
        @Test
        fun `should decrease the quality by 1`() {
            assertEquals(
                Quality.Standard.of(49)!!,
                STANDARD.age(Quality.FIFTY)
            )

            assertEquals(
                Quality.Standard.of(48)!!,
                STANDARD.age(STANDARD.age(Quality.FIFTY))
            )

            assertEquals(
                Quality.ZERO,
                STANDARD.age(Quality.ZERO)
            )
        }
    }

    @Nested
    inner class ExpiredTest {
        @Test
        fun `should decrease the quality by 2`() {
            assertEquals(
                Quality.Standard.of(48)!!,
                EXPIRED.age(Quality.FIFTY)
            )

            assertEquals(
                Quality.Standard.of(46)!!,
                EXPIRED.age(EXPIRED.age(Quality.FIFTY))
            )
        }
        @Test
        fun `should be able to decrease the quality to zero`() {
            assertEquals(
                Quality.ZERO,
                EXPIRED.age(Quality.Standard.of(1)!!)
            )
        }
    }

    @Nested
    inner class RefinementTest {
        @Test
        fun `should increase the quality by 1`() {
            assertEquals(
                Quality.Standard.of(1)!!,
                REFINEMENT.age(Quality.ZERO)
            )

            assertEquals(
                Quality.Standard.of(2)!!,
                REFINEMENT.age(REFINEMENT.age(Quality.ZERO))
            )
        }
    }
}