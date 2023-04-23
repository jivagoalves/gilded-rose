package com.gildedrose.repositories

import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem

interface IStockRepository {
    fun findAll(): List<StockEntry>
    fun save(validItem: ValidItem): StockEntry
    fun deleteById(id: ItemId): Boolean
}
