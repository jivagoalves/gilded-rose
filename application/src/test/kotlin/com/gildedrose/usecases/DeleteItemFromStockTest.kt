package com.gildedrose.usecases

import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.FakeStockRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class DeleteItemFromStockTest {
    private val fakeStockRepo = FakeStockRepository()

    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan2nd = LocalDate.parse("2023-01-02")

    private val validItem = ValidItem(
        N("Orange")!!,
        OneOf.JustValid(Valid(ShelfLife(jan1st, jan2nd))!!),
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

    @Test
    fun `should delete by id from the stock repository`() {
        AddItemToStock(fakeStockRepo).addItem(validItemDTO)
        assertEquals(listOf(validItem), fakeStockRepo.findAll().map(StockEntry::item))
        DeleteItemFromStock(fakeStockRepo).deleteById(ItemId.random())
        assertEquals(emptyList(), fakeStockRepo.findAll())
    }
}