package com.gildedrose.usecases

import com.gildedrose.domain.items.ItemId
import com.gildedrose.repositories.IStockRepository

interface IDeleteItemFromStock {
    fun deleteById(id: ItemId): Boolean
}

class DeleteItemFromStock(
    private val stockRepository: IStockRepository
) : IDeleteItemFromStock {
    override fun deleteById(id: ItemId) =
        stockRepository.deleteById(id)
}