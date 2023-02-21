package com.gildedrose.usecases

import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.IStockRepository

interface IAddItemToStock {
    fun addItem(validItem: ValidItem)
}

class AddItemToStock(private val stockRepository: IStockRepository) : IAddItemToStock {
    override fun addItem(validItem: ValidItem) {
        stockRepository.save(validItem)
    }
}