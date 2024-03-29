package com.gildedrose.domain.contracts.lifecycle

import arrow.core.invalidNel
import arrow.core.valid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ValidShelfLife.InvalidLifecycle
import com.gildedrose.usecases.ItemDTO
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class ValidShelfLifeTest {
    private val today = "2023-01-02"
    private val yesterday = "2023-01-01"
    private val invalidShelfLifeDTO = ItemDTO.ShelfLifeDTO(today, yesterday)
    private val validShelfLifeDTO = ItemDTO.ShelfLifeDTO(yesterday, today)
    private val validShelfLife = Valid(
        ShelfLife(
            yesterday.toLocalDate(),
            today.toLocalDate()
        )
    )!!

    @Test
    fun `should validate shelf life`() {
        assertEquals(
            validShelfLife.valid(),
            ValidShelfLife.validatedFrom(validShelfLifeDTO)
        )
        assertEquals(
            InvalidLifecycle.invalidNel(),
            ValidShelfLife.validatedFrom(invalidShelfLifeDTO)
        )
        assertEquals(
            InvalidLifecycle.invalidNel(),
            ValidShelfLife.validatedFrom(ItemDTO.ShelfLifeDTO("invalid-date", today))
        )
        assertEquals(
            InvalidLifecycle.invalidNel(),
            ValidShelfLife.validatedFrom(ItemDTO.ShelfLifeDTO(today, "invalid-date"))
        )
    }

    @Nested
    inner class InvalidLifecycleTest {
        @Test
        fun `should have description and string representation`() {
            assertEquals(
                "Lifecycle is not valid",
                InvalidLifecycle.description
            )
            assertEquals(
                "InvalidLifecycle",
                InvalidLifecycle.toString()
            )
        }
    }
}

private fun String.toLocalDate(): LocalDate =
    LocalDate.parse(this)
