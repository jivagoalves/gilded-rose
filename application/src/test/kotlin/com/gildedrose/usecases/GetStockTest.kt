package com.gildedrose.usecases

import com.gildedrose.domain.N
import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import com.gildedrose.repositories.FakeStockRepository
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class GetStockTest {
    private val entries = mutableListOf<ValidItem>()
    private val fakeStockRepo = FakeStockRepository(entries)

    private val validItem = ValidItem(
        N("Orange")!!,
        OneOf.JustValid(Valid(ShelfLife.NOW)!!),
        Standard.of(9)!!
    )
    private val getStock = GetStock(fakeStockRepo)
    private val now = LocalDate.now()
    private val tomorrow = now.plusDays(1)

    @Test
    fun `should retrieve all items from the repository aged as of date`() {
        assertEquals(Stock.EMPTY, getStock.asOf(now))
        entries.add(validItem)
        assertEquals(Stock.of(listOf(validItem.asOf(tomorrow))), getStock.asOf(tomorrow))
    }
}