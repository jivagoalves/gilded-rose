package gildedrose.domain.contracts.aging

import gildedrose.domain.contracts.aging.Aging.*
import gildedrose.domain.quality.Legendary
import gildedrose.domain.quality.Quality
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import kotlin.test.assertEquals
import gildedrose.domain.quality.Standard as StandardQuality

class AgingTest {
    private fun ageable(quality: Quality, sellIn: Int = 0): Ageable =
        object : Ageable {
            override val registeredOn: LocalDate
                get() = LocalDate.now()
            override val sellBy: LocalDate
                get() = LocalDate.now().plusDays(sellIn.toLong())
            override val quality: Quality
                get() = quality
            override fun withQuality(quality: Quality): Ageable =
                this
        }

    @Nested
    inner class NoneTest {
        @Test
        fun `should always keep the original quality`() {
            assertEquals(
                StandardQuality.ZERO,
                None.age(ageable(StandardQuality.ZERO))
            )

            assertEquals(
                StandardQuality.of(30)!!,
                None.age(ageable(StandardQuality.of(30)!!))
            )
        }
    }

    @Nested
    inner class StandardTest {
        @Test
        fun `should decrease the quality by 1`() {
            assertEquals(
                StandardQuality.of(49)!!,
                Standard.age(ageable(StandardQuality.FIFTY))
            )

            assertEquals(
                StandardQuality.of(48)!!,
                Standard.age(ageable(StandardQuality.of(49)!!))
            )

            assertEquals(
                StandardQuality.ZERO,
                Standard.age(ageable(StandardQuality.ZERO))
            )
        }
    }

    @Nested
    inner class ExpiredTest {
        @Test
        fun `should decrease the quality by 2`() {
            assertEquals(
                StandardQuality.of(48)!!,
                Expired.age(ageable(StandardQuality.FIFTY))
            )

            assertEquals(
                StandardQuality.of(46)!!,
                Expired.age(ageable(StandardQuality.of(48)!!))
            )
        }
        @Test
        fun `should be able to decrease the quality to zero`() {
            assertEquals(
                StandardQuality.ZERO,
                Expired.age(ageable(StandardQuality.of(1)!!))
            )
        }
    }

    @Nested
    inner class RefinementTest {
        @Test
        fun `should increase the quality by 1`() {
            assertEquals(
                StandardQuality.of(1)!!,
                Improvement.age(ageable(StandardQuality.ZERO))
            )

            assertEquals(
                StandardQuality.of(2)!!,
                Improvement.age(ageable(StandardQuality.of(1)!!))
            )
        }
    }

    @Nested
    inner class TimedRefinementTest {

        @ParameterizedTest(name = "with {0} days")
        @ValueSource(ints = [0, 5, 6, 10, 11, 9999])
        fun `should keep the quality at 50 even with days left to sell by`(sellIn: Int) {
            assertEquals(
                StandardQuality.FIFTY,
                TimedImprovement.age(
                    ageable(
                        StandardQuality.FIFTY,
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
                    StandardQuality.of(1)!!,
                    TimedImprovement.age(
                        ageable(
                            StandardQuality.ZERO,
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
                    StandardQuality.of(2)!!,
                    TimedImprovement.age(
                        ageable(
                            StandardQuality.ZERO,
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
                    StandardQuality.of(3)!!,
                    TimedImprovement.age(
                        ageable(
                            StandardQuality.ZERO,
                            sellIn
                        )
                    )
                )
            }
        }

        @Nested
        @DisplayName("when the are no more days left to sell by")
        inner class NoMoreDaysToSellBy {
            private val sellIn = -1

            @Test
            fun `should drop the quality to zero`() {
                assertEquals(
                    StandardQuality.ZERO,
                    TimedImprovement.age(
                        ageable(
                            StandardQuality.of(10)!!,
                            sellIn
                        )
                    )
                )
            }

            @Test
            fun `should not change the quality type`() {
                assertEquals(
                    StandardQuality.ZERO,
                    TimedImprovement.age(
                        ageable(
                            StandardQuality.ZERO,
                            sellIn
                        )
                    )
                )

                assertEquals(
                    Legendary.ZERO,
                    TimedImprovement.age(
                        ageable(
                            Legendary.ZERO,
                            sellIn
                        )
                    )
                )
            }
        }
    }
}
