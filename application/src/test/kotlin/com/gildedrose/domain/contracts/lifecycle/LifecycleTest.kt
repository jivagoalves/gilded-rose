package com.gildedrose.domain.contracts.lifecycle

import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days

class LifecycleTest {
    private fun lifecycleBetween(from: LocalDate, until: LocalDate) = object : Lifecycle {
        override val registeredOn: LocalDate
            get() = from
        override val sellBy: LocalDate
            get() = until
    }

    @ParameterizedTest(name = "should sell in {2} days from {0} until {1}")
    @CsvSource(value = [
        "2023-01-02,2023-01-01,-1",
        "2023-01-01,2023-01-01,0",
        "2023-01-01,2023-01-02,1",
        "2023-01-01,2023-01-03,2"
    ])
    fun `should sell in days according to the period between registration and expiration`(
        from: LocalDate,
        until: LocalDate,
        n: Int
    ) {
        assertEquals(n.days, lifecycleBetween(from, until).sellIn)
    }
}