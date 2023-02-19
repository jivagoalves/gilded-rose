package com.domain.items

import com.gildedrose.day
import com.gildedrose.domain.N
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.aging.Ageable
import com.gildedrose.domain.contracts.aging.Aging
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Quality
import com.gildedrose.domain.quality.Standard
import com.gildedrose.plus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import kotlin.test.assertNotNull

class ValidItemTest {
    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan2nd = jan1st + 1.day

    @Nested
    @DisplayName("when not expired")
    inner class WhenNotExpiredTest {
        private val nonExpiredValidItem = ValidItem(
            name = N("Apple")!!,
            lifecycle = JustValid(Valid(ShelfLife(jan1st, jan2nd))!!),
            quality = Standard.FIFTY
        )

        @Test
        fun `should be valid`() {
            assertNotNull(nonExpiredValidItem)
        }
    }

    @Nested
    @DisplayName("::degrade")
    inner class DegradingValidItemTest {
        private val validItem = ValidItem(
            name = N("Pen")!!,
            lifecycle = JustValid(Valid(ShelfLife.NOW)!!),
            quality = Standard.FIFTY,
        )

        @Test
        fun `should use standard degradation by default`() {
            assertEquals(validItem.aging, Aging.Standard)
        }

        @ParameterizedTest(name = "hardcoded {0}")
        @ValueSource(ints = [1, 2, 3, 4, 5])
        fun `should accept different degradation strategies`(qualityValue: Int) {
            val pen = validItem.copy(aging = object : Aging {
                override fun age(ageable: Ageable): Quality =
                    Standard.of(qualityValue)!!
            })

            assertEquals(
                Standard.of(qualityValue),
                pen.age().quality
            )
        }
    }
}