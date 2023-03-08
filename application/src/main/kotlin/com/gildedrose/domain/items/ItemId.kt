package com.gildedrose.domain.items

@JvmInline
value class ItemId private constructor(val value: Long) {
    companion object {
        fun of(value: Long) : ItemId? = ItemId(value)
    }

    override fun toString(): String = value.toString()
}