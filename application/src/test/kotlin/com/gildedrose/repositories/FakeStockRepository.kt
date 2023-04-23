package com.gildedrose.repositories

import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem

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

    override fun deleteById(id: ItemId): Boolean {
        entries.clear()
        return true
    }
}