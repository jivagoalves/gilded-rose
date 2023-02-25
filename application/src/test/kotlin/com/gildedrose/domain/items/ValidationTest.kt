package com.gildedrose.domain.items

import arrow.core.invalid
import arrow.core.valid
import com.gildedrose.domain.N
import com.gildedrose.domain.Name
import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.contracts.lifecycle.ValidShelfLife
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.ItemDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ValidationTest {
    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan5th = LocalDate.parse("2023-01-05")

    private val validItem = ValidItem(
        N("Orange")!!,
        OneOf.JustValid(Valid(ShelfLife(jan1st, jan5th))!!),
        Standard.of(9)!!
    )

    private fun itemDTO(
        name: String,
        quality: Int,
        registeredOn: String,
        sellBy: String
    ): ItemDTO {
        return object : ItemDTO {
            override val name: String
                get() = name
            override val quality: Int
                get() = quality
            override val registeredOn: String
                get() = registeredOn
            override val sellBy: String
                get() = sellBy

        }
    }

    @Test
    fun `should parse successfully into valid item`() {
        assertEquals(
            validItem.valid(),
            Validation.validate(
                itemDTO(
                    "Orange",
                    9,
                    "2023-01-01",
                    "2023-01-05"
                )
            )
        )
    }

    @Test
    fun `should collect errors for invalid item`() {
        assertEquals(
            listOf(
                Name.BlankName,
                Standard.InvalidQuality,
                ValidShelfLife.InvalidLifecycle,
            ).invalid(),
            Validation.validate(
                itemDTO(
                    "",
                    -1,
                    "2023-01-05",
                    "2023-01-01",
                )
            )
        )
    }
}