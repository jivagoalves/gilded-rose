package com.gildedrose.domain.items

import com.gildedrose.day
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.aging.Ageable
import com.gildedrose.domain.contracts.aging.Aging
import com.gildedrose.domain.contracts.lifecycle.LegendaryLife
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.plus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.days

class ValidItemTest {
    private val dec31st = LocalDate.parse("2022-12-31")
    private val jan1st = dec31st + 1.day
    private val jan2nd = jan1st + 1.day
    private val jan3rd = jan1st + 2.days

    @Nested
    @DisplayName("when not expired")
    inner class WhenNotExpiredTest {
        private val nonExpiredValidItem = ValidItem(
            name = N("Apple")!!,
            lifecycle = JustValid(Valid(ShelfLife(jan1st, jan2nd))!!),
            quality = StandardQuality.FIFTY
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
            quality = StandardQuality.FIFTY,
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
                    StandardQuality.of(qualityValue)!!
            })

            assertEquals(
                StandardQuality.of(qualityValue),
                pen.age().quality
            )
        }
    }

    @Nested
    @DisplayName("::asOf - quality behaviour")
    inner class AgingUntilAsOfDateTest {
        private val apple = ValidItem(
            name = N("Apple")!!,
            lifecycle = JustValid(Valid(ShelfLife(jan1st, jan2nd))!!),
            quality = StandardQuality.FIFTY
        )

        private val feb1st = LocalDate.parse("2023-02-01")
        private val feb19th = LocalDate.parse("2023-02-19")

        @Test
        fun `should age until as of date`() {
            assertEquals(apple.age().quality, apple.asOf(jan2nd).quality)
            assertEquals(StandardQuality.of(50 - 31), apple.asOf(feb1st).quality)
            assertEquals(StandardQuality.of(50 - 49), apple.asOf(feb19th).quality)
        }

        @Test
        fun `should not age when as of date is before registration`() {
            assertEquals(apple.quality, apple.asOf(dec31st).quality)
        }
    }

    @Nested
    @DisplayName("::asOf - sellIn behaviour")
    inner class SellInAsOfTest {
        private val apple = ValidItem(
            name = N("Apple")!!,
            lifecycle = JustValid(Valid(ShelfLife(jan1st, jan2nd))!!),
            quality = StandardQuality.FIFTY
        )

        @Test
        fun `should remember sell in as of date`() {
            assertEquals(2.days, apple.asOf(jan1st).sellIn)
            assertEquals(1.days, apple.asOf(jan2nd).sellIn)
            assertEquals(0.days, apple.asOf(jan3rd).sellIn)
        }

        private val registeredOn = LocalDate.parse("2023-02-20")
        private val sellBy = LocalDate.parse("2023-03-30")
        private val asOfDate = LocalDate.parse("2023-02-26")
        private val orange = ValidItem(
            name = N("Orange")!!,
            lifecycle = JustValid(Valid(ShelfLife(registeredOn, sellBy))!!),
            quality = StandardQuality.of(33)!!
        )

        @Test
        fun `should remember sell in as of date across months`() {
            assertEquals(33.days, orange.asOf(asOfDate).sellIn)
        }


        private val sulfuras = ValidItem(
            name = N("Sulfuras")!!,
            lifecycle = JustValid(Valid(LegendaryLife(
                LocalDate.MAX.minusDays(1)
            ))!!),
            quality = LegendaryQuality.of(80)
        )
        @Test
        fun `should calculate sell in for legendary items`() {
            assertEquals(1.day, sulfuras.sellIn)
        }
    }

}