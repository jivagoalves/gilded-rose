package com.gildedrose.usecases

import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.Validated
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.items.Validation
import com.gildedrose.domain.items.ValidationError
import com.gildedrose.repositories.IStockRepository

interface ItemDTO {
    val name: String
    val quality: Int
    val registeredOn: String
    val sellBy: String
}

interface IAddItemToStock {
    fun addItem(itemDTO: ItemDTO): Validated<List<ValidationError>, ValidItem>
}

class AddItemToStock(private val stockRepository: IStockRepository) : IAddItemToStock {

    override fun addItem(itemDTO: ItemDTO): Validated<List<ValidationError>, ValidItem> =
        when(val vItem = Validation.validate(itemDTO)) {
            is Valid -> vItem.also { stockRepository.save(it.value) }
            is Invalid -> vItem
        }

}