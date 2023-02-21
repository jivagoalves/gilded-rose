package com.gildedrose.repositories

import com.gildedrose.domain.items.ValidItem

class FakeStockRepository(
    private val entries: MutableList<ValidItem> = mutableListOf()
) : IStockRepository {
    override fun findAll(): List<ValidItem> {
        return entries
    }

    override fun save(validItem: ValidItem) {
        entries.add(validItem)
    }
}