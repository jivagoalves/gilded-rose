package gildedrose.domain.items

import gildedrose.day
import gildedrose.domain.N
import gildedrose.domain.Quality
import gildedrose.domain.ShelfLife
import gildedrose.domain.contracts.Expired
import gildedrose.domain.contracts.OneOf.JustExpired
import gildedrose.plus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertNotNull

class ExpiredItemTest {
    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan2nd = jan1st + 1.day

    @Nested
    @DisplayName("when expired")
    inner class WhenExpiredTest {
        private fun expiredItem(quality: Quality): ExpiredItem? = ExpiredItem(
            name = N("Apple"),
            lifecycle = JustExpired(Expired(ShelfLife(jan2nd, jan1st))!!),
            quality = quality
        )

        @Test
        fun `should be valid`() {
            assertNotNull(expiredItem(quality = Quality.ZERO))
        }
    }

    @Nested
    @DisplayName("::degrade")
    inner class DegradingExpiredItemsTest {
        private val jan1st = LocalDate.parse("2023-01-01")
        private val jan2nd = jan1st + 1.day

        @Test
        fun `should never decrease the quality below zero`() {
            val expiredItem = ExpiredItem(
                name = N("Orange"),
                lifecycle = JustExpired(Expired(ShelfLife(jan2nd, jan1st))!!),
                quality = Quality.ZERO
            )!!

            assertEquals(
                Quality.ZERO,
                expiredItem
                    .degrade().quality
            )
            assertEquals(
                Quality.ZERO,
                expiredItem
                    .degrade()
                    .degrade().quality
            )
        }

        @Test
        fun `should decrease twice as fast`() {
            val expiredItem = ExpiredItem(
                name = N("Orange"),
                lifecycle = JustExpired(Expired(ShelfLife(jan2nd, jan1st))!!),
                quality = Quality.FIFTY
            )!!

            assertEquals(
                Quality.of(48)!!,
                expiredItem.degrade().quality
            )
            assertEquals(
                Quality.of(46)!!,
                expiredItem
                    .degrade()
                    .degrade().quality
            )
        }
    }
}