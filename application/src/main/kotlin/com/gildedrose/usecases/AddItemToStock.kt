package com.gildedrose.usecases

import arrow.core.Validated
import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.items.Validation
import com.gildedrose.domain.items.ValidationError
import com.gildedrose.repositories.IStockRepository

interface ItemDTO {
    val name: String
    val quality: Int
    val registeredOn: String
    val sellBy: String

    val shelfLife: ShelfLifeDTO
        get() =
            ShelfLifeDTO(registeredOn, sellBy)

    data class ShelfLifeDTO(val registeredOn: String, val sellBy: String)
}

fun interface IAddItemToStock {
    fun addItem(itemDTO: ItemDTO): Validated<List<ValidationError>, StockEntry>
}

class AddItemToStock(private val stockRepository: IStockRepository) : IAddItemToStock {

    override fun addItem(itemDTO: ItemDTO): Validated<List<ValidationError>, StockEntry> =
        Validation.validate(itemDTO).map { stockRepository.save(it) }

}