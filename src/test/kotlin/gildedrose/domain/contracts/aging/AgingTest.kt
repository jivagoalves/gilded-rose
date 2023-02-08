package gildedrose.domain.contracts.aging

import gildedrose.domain.Quality
import gildedrose.domain.contracts.aging.Aging.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AgingTest {
    private fun ageable(quality: Quality): Ageable {
       return object : Ageable {
           override val quality: Quality
               get() = quality

       }
    }

    @Nested
    inner class NoneTest {
        @Test
        fun `should always keep the original quality`() {
            assertEquals(
                Quality.ZERO,
                NONE.age(ageable(Quality.ZERO))
            )

            assertEquals(
                Quality.Standard.of(30)!!,
                NONE.age(ageable(Quality.Standard.of(30)!!))
            )
        }
    }

    @Nested
    inner class StandardTest {
        @Test
        fun `should decrease the quality by 1`() {
            assertEquals(
                Quality.Standard.of(49)!!,
                STANDARD.age(ageable(Quality.FIFTY))
            )

            assertEquals(
                Quality.Standard.of(48)!!,
                STANDARD.age(ageable(Quality.Standard.of(49)!!))
            )

            assertEquals(
                Quality.ZERO,
                STANDARD.age(ageable(Quality.ZERO))
            )
        }
    }

    @Nested
    inner class ExpiredTest {
        @Test
        fun `should decrease the quality by 2`() {
            assertEquals(
                Quality.Standard.of(48)!!,
                EXPIRED.age(ageable(Quality.FIFTY))
            )

            assertEquals(
                Quality.Standard.of(46)!!,
                EXPIRED.age(ageable(Quality.Standard.of(48)!!))
            )
        }
        @Test
        fun `should be able to decrease the quality to zero`() {
            assertEquals(
                Quality.ZERO,
                EXPIRED.age(ageable(Quality.Standard.of(1)!!,))
            )
        }
    }

    @Nested
    inner class RefinementTest {
        @Test
        fun `should increase the quality by 1`() {
            assertEquals(
                Quality.Standard.of(1)!!,
                REFINEMENT.age(ageable(Quality.ZERO))
            )

            assertEquals(
                Quality.Standard.of(2)!!,
                REFINEMENT.age(ageable(Quality.Standard.of(1)!!))
            )
        }
    }
}