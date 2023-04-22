package com.gildedrose.repositories

import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.usecases.Persisted

interface IStockRepository {
    fun findAll(): List<ValidItem>
    fun save(validItem: ValidItem): Persisted
    fun deleteById(id: ItemId)
}
