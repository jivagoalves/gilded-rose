package com.gildedrose.usecases

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.FakeStockRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class AddItemToStockTest {
    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan2nd = LocalDate.parse("2023-01-02")

    private val validItem = ValidItem(
        N("Orange")!!,
        JustValid(Valid(ShelfLife(jan1st, jan2nd))!!),
        StandardQuality.of(9)!!
    )

    private val validItemDTO: ItemDTO =
        object : ItemDTO {
            override val name: String
                get() = "Orange"
            override val quality: Int
                get() = 9
            override val registeredOn: String
                get() = "2023-01-01"
            override val sellBy: String
                get() = "2023-01-02"
        }

    private val invalidItemDTO: ItemDTO =
        object : ItemDTO {
            override val name: String
                get() = ""
            override val quality: Int
                get() = -1
            override val registeredOn: String
                get() = "2023-01-01"
            override val sellBy: String
                get() = "2023-01-02"

        }

    private val fakeStockRepo = FakeStockRepository()

    @Test
    fun `should validate items before adding to the stock repository`() {
        assertEquals(emptyList(), fakeStockRepo.findAll())
        AddItemToStock(fakeStockRepo).addItem(invalidItemDTO)
        assertEquals(emptyList(), fakeStockRepo.findAll())
        AddItemToStock(fakeStockRepo).addItem(validItemDTO)
        assertEquals(listOf(validItem), fakeStockRepo.findAll().map(StockEntry::item))
    }
}