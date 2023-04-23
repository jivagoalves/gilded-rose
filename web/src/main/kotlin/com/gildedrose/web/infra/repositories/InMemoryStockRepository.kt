package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.IStockRepository

class InMemoryStockRepository : IStockRepository {
    private val entries = mutableListOf<StockEntry>()

    override fun findAll(): List<StockEntry> = entries

    override fun save(validItem: ValidItem): StockEntry =
        StockEntry(ItemId.random(), validItem).also {
            entries.add(it)
        }

    override fun deleteById(id: ItemId): Boolean {
        TODO("Not yet implemented")
    }

}
