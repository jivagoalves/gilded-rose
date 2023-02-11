package gildedrose.domain.contracts.aging

import gildedrose.domain.Quality
import gildedrose.domain.contracts.aging.Aging.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import kotlin.test.assertEquals

class AgingTest {
    private fun ageable(quality: Quality, sellIn: Int = 0): Ageable =
        object : Ageable {
            override val registeredOn: LocalDate
                get() = LocalDate.now()
            override val sellBy: LocalDate
                get() = LocalDate.now().plusDays(sellIn.toLong())
            override val quality: Quality
                get() = quality

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
                EXPIRED.age(ageable(Quality.Standard.of(1)!!))
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

    @Nested
    inner class TimedRefinementTest {

        @ParameterizedTest(name = "with {0} days")
        @ValueSource(ints = [0, 5, 6, 10, 11, 9999])
        fun `should keep the quality at 50 even with days left to sell by`(sellIn: Int) {
            assertEquals(
                Quality.FIFTY,
                TIMED_REFINEMENT.age(
                    ageable(
                        Quality.FIFTY,
                        sellIn
                    )
                )
            )
        }

        @Nested
        @DisplayName("when there are more than 10 days left to sell by")
        inner class MoreThanTenDays {
            @ParameterizedTest(name = "with {0} days")
            @ValueSource(ints = [11, 12, 13, 50, 100, 1000])
            fun `should increase the quality by 1`(sellIn: Int) {
                assertEquals(
                    Quality.Standard.of(1)!!,
                    TIMED_REFINEMENT.age(
                        ageable(
                            Quality.ZERO,
                            sellIn
                        )
                    )
                )
            }
        }

        @Nested
        @DisplayName("when there are between 10 or 6 days left to sell by")
        inner class BetweenTenAndSixDaysLeft {
            @ParameterizedTest(name = "with {0} days")
            @ValueSource(ints = [10, 9, 8, 7, 6])
            fun `should increase the quality by 2`(sellIn: Int) {
                assertEquals(
                    Quality.Standard.of(2)!!,
                    TIMED_REFINEMENT.age(
                        ageable(
                            Quality.ZERO,
                            sellIn
                        )
                    )
                )
            }
        }

        @Nested
        @DisplayName("when there are between 5 or 0 days left to sell by")
        inner class BetweenFiveAndZeroDaysLeft {
            @ParameterizedTest(name = "with {0} days")
            @ValueSource(ints = [5, 4, 3, 2, 1, 0])
            fun `should increase the quality by 3`(sellIn: Int) {
                assertEquals(
                    Quality.Standard.of(3)!!,
                    TIMED_REFINEMENT.age(
                        ageable(
                            Quality.ZERO,
                            sellIn
                        )
                    )
                )
            }
        }

        @Nested
        @DisplayName("when the are no more days left to sell by")
        inner class NoMoreDaysToSellBy {
            @ParameterizedTest(name = "with {0} days")
            @ValueSource(ints = [-1, -2, -5, -10, -50, -100])
            fun `should drop the quality to zero`(sellIn: Int) {
                assertEquals(
                    Quality.ZERO,
                    TIMED_REFINEMENT.age(
                        ageable(
                            Quality.Standard.of(10)!!,
                            sellIn
                        )
                    )
                )
            }
        }
    }
}
