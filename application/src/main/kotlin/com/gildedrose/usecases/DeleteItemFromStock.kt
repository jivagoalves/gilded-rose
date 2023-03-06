package com.gildedrose.usecases

interface IDeleteItemFromStock {
    fun deleteById(id: ItemId)
}

class DeleteItemFromStock : IDeleteItemFromStock {
    override fun deleteById(id: ItemId): Nothing =
        TODO("Not implemented yet")
}

@JvmInline
value class ItemId private constructor(val value: Long) {
    companion object {
        fun of(value: Long) : ItemId? = ItemId(value)
    }
}
