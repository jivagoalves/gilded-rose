package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.IStockRepository
import com.gildedrose.usecases.Persisted

class InMemoryStockRepository : IStockRepository {
    private val entries = mutableListOf<ValidItem>()

    override fun findAll(): List<ValidItem> = entries

    override fun save(validItem: ValidItem): Persisted {
        entries.add(validItem)
        return Persisted(ItemId.of(1)!!, validItem)
    }

    override fun deleteById(id: ItemId) {
        TODO("Not yet implemented")
    }

}
