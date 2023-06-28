package com.gildedrose.usecases

import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.FakeStockRepository
import com.gildedrose.repositories.IStockRepository
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

    @Test
    fun `should return true when saved successfully by the stock repository`() {
        assertTrue(DeleteItemFromStock(repositoryStub(deleteById = true)).deleteById(ItemId.random()))
        assertFalse(DeleteItemFromStock(repositoryStub(deleteById = false)).deleteById(ItemId.random()))
    }

    private fun repositoryStub(deleteById: Boolean) = object : IStockRepository {
        override fun findAll(): List<StockEntry> = TODO("Unexpected call")
        override fun save(validItem: ValidItem): StockEntry = TODO("Unexpected call")
        override fun deleteById(id: ItemId): Boolean = deleteById
    }
}