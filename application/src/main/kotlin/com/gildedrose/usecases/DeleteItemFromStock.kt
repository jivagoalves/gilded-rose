package com.gildedrose.usecases

import com.gildedrose.domain.items.ItemId

interface IDeleteItemFromStock {
    fun deleteById(id: ItemId)
}

class DeleteItemFromStock : IDeleteItemFromStock {
    override fun deleteById(id: ItemId): Nothing =
        TODO("Not implemented yet")
}