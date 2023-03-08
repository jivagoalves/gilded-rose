package com.gildedrose.repositories

import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.items.ItemId
import com.gildedrose.usecases.Persisted

class FakeStockRepository(
    private val entries: MutableList<ValidItem> = mutableListOf()
) : IStockRepository {
    override fun findAll(): List<ValidItem> {
        return entries
    }

    override fun save(validItem: ValidItem): Persisted {
        entries.add(validItem)
        return Persisted(ItemId.of(1)!!, validItem)
    }
}