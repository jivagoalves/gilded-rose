package com.gildedrose.repositories

import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.StockEntry

class FakeStockRepository(
    private val entries: MutableList<StockEntry> = mutableListOf()
) : IStockRepository {
    override fun findAll(): List<StockEntry> {
        return entries
    }

    override fun save(validItem: ValidItem): StockEntry =
        StockEntry(ItemId.random(), validItem).also {
            entries.add(it)
        }

    override fun deleteById(id: ItemId) {
        entries.clear()
    }
}