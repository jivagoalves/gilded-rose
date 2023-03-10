package com.gildedrose.usecases

import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.Validated
import arrow.core.valid
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.ValidItem
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

data class Persisted(val id: ItemId, val validItem: ValidItem)

interface IAddItemToStock {
    fun addItem(itemDTO: ItemDTO): Validated<List<ValidationError>, Persisted>
}

class AddItemToStock(private val stockRepository: IStockRepository) : IAddItemToStock {

    override fun addItem(itemDTO: ItemDTO): Validated<List<ValidationError>, Persisted> =
        when (val vItem = Validation.validate(itemDTO)) {
            is Valid -> vItem.let { stockRepository.save(it.value).valid() }
            is Invalid -> vItem
        }

}