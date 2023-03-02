package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.IStockRepository

class InMemoryStockRepository : IStockRepository {
    private val entries = mutableListOf<ValidItem>()

    override fun findAll(): List<ValidItem> = entries

    override fun save(validItem: ValidItem) {
        entries.add(validItem)
    }

}
