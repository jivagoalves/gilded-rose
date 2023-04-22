package com.gildedrose.repositories

import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.StockEntry

interface IStockRepository {
    fun findAll(): List<StockEntry>
    fun save(validItem: ValidItem): StockEntry
    fun deleteById(id: ItemId)
}
