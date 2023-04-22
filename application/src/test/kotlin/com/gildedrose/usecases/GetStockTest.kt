package com.gildedrose.usecases

import com.gildedrose.domain.Stock
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

class GetStockTest {
    private val entries = mutableListOf<StockEntry>()
    private val fakeStockRepo = FakeStockRepository(entries)

    private val validItem = ValidItem(
        N("Orange")!!,
        OneOf.JustValid(Valid(ShelfLife.NOW)!!),
        StandardQuality.of(9)!!
    )
    private val entry = StockEntry(ItemId.random(), validItem)
    private val getStock = GetStock(fakeStockRepo)
    private val now = LocalDate.now()
    private val tomorrow = now.plusDays(1)

    @Test
    fun `should retrieve all items from the repository aged as of date`() {
        assertEquals(Stock.EMPTY, getStock.asOf(now))
        entries.add(entry)
        assertEquals(Stock.of(listOf(entry.asOf(tomorrow))), getStock.asOf(tomorrow))
    }
}