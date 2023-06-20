package com.gildedrose.domain.contracts.aging

import com.gildedrose.domain.items.Quality
import com.gildedrose.domain.items.StandardQuality
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AgeableTest {
    private fun ageableBetween(from: LocalDate, until: LocalDate) = object : Ageable {
        override fun withQuality(quality: Quality): Ageable =
            this

        override val registeredOn: LocalDate
            get() = from
        override val sellBy: LocalDate
            get() = until
        override val quality: Quality
            get() = StandardQuality.ZERO
    }

    @Test
    fun `should be valid when registration is before sell by date`() {
        Assertions.assertTrue(
            ageableBetween(
                LocalDate.parse("2022-01-01"),
                LocalDate.parse("2022-01-02")
            ).isValid
        )
        Assertions.assertTrue(
            ageableBetween(
                LocalDate.parse("2022-01-02"),
                LocalDate.parse("2022-01-02")
            ).isValid
        )
        Assertions.assertFalse(
            ageableBetween(
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-02")
            ).isValid
        )
    }

    @Test
    fun `should be expired when registration is after sell by date`() {
        Assertions.assertTrue(
            ageableBetween(
                LocalDate.parse("2022-01-02"),
                LocalDate.parse("2022-01-01")
            ).isExpired
        )
        Assertions.assertFalse(
            ageableBetween(
                LocalDate.parse("2022-01-02"),
                LocalDate.parse("2022-01-02")
            ).isExpired
        )
    }
}