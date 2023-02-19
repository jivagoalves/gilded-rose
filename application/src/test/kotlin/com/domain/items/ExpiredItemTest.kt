package com.domain.items

import com.gildedrose.day
import com.gildedrose.domain.N
import com.gildedrose.domain.contracts.Expired
import com.gildedrose.domain.contracts.OneOf.JustExpired
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ExpiredItem
import com.gildedrose.domain.quality.Quality
import com.gildedrose.domain.quality.Standard
import com.gildedrose.plus
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
        private fun expiredItem(quality: Quality): ExpiredItem = ExpiredItem(
            name = N("Apple")!!,
            lifecycle = JustExpired(Expired(ShelfLife(jan2nd, jan1st))!!),
            quality = quality
        )

        @Test
        fun `should be valid`() {
            assertNotNull(expiredItem(quality = Standard.ZERO))
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
                name = N("Orange")!!,
                lifecycle = JustExpired(Expired(ShelfLife(jan2nd, jan1st))!!),
                quality = Standard.ZERO
            )

            assertEquals(
                Standard.ZERO,
                expiredItem
                    .age().quality
            )
            assertEquals(
                Standard.ZERO,
                expiredItem
                    .age()
                    .age().quality
            )
        }

        @Test
        fun `should decrease twice as fast`() {
            val expiredItem = ExpiredItem(
                name = N("Orange")!!,
                lifecycle = JustExpired(Expired(ShelfLife(jan2nd, jan1st))!!),
                quality = Standard.FIFTY
            )

            assertEquals(
                Standard.of(48)!!,
                expiredItem.age().quality
            )
            assertEquals(
                Standard.of(46)!!,
                expiredItem
                    .age()
                    .age().quality
            )
        }
    }
}