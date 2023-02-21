package com.gildedrose.repositories

import com.gildedrose.domain.items.ValidItem

interface IStockRepository {
    fun findAll(): List<ValidItem>
    fun save(validItem: ValidItem)
}
