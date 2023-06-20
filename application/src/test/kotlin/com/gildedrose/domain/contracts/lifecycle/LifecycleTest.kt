package com.gildedrose.domain.contracts.lifecycle

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate
import kotlin.time.Duration.Companion.days

class LifecycleTest {
    private fun lifecycleBetween(from: LocalDate, until: LocalDate) = object : Lifecycle {
        override val registeredOn: LocalDate
            get() = from
        override val sellBy: LocalDate
            get() = until
    }

    @ParameterizedTest(name = "should sell in {2} days from {0} until {1}")
    @CsvSource(
        value = [
            "2023-01-03,2023-01-01,-1",
            "2023-01-02,2023-01-01,0",
            "2023-01-01,2023-01-01,1",
            "2023-01-01,2023-01-02,2",
            "2023-01-01,2023-01-03,3",
            "2023-01-01,2023-02-01,32"
        ]
    )
    fun `should sell in days according to the period between registration and expiration`(
        from: LocalDate,
        until: LocalDate,
        n: Int
    ) {
        assertEquals(n.days, lifecycleBetween(from, until).sellIn)
    }

    @Test
    fun `should be valid when registration is before sell by date`() {
        assertTrue(
            lifecycleBetween(
                LocalDate.parse("2022-01-01"),
                LocalDate.parse("2022-01-02")
            ).isValid
        )
        assertTrue(
            lifecycleBetween(
                LocalDate.parse("2022-01-02"),
                LocalDate.parse("2022-01-02")
            ).isValid
        )
        assertFalse(
            lifecycleBetween(
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-02")
            ).isValid
        )
    }

    @Test
    fun `should be expired when registration is after sell by date`() {
        assertTrue(
            lifecycleBetween(
                LocalDate.parse("2022-01-02"),
                LocalDate.parse("2022-01-01")
            ).isExpired
        )
        assertFalse(
            lifecycleBetween(
                LocalDate.parse("2022-01-02"),
                LocalDate.parse("2022-01-02")
            ).isExpired
        )
    }
}