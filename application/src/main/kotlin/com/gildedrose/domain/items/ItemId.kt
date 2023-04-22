package com.gildedrose.domain.items

import kotlin.random.Random

@JvmInline
value class ItemId private constructor(val value: Long) {
    companion object {
        fun of(value: Long) : ItemId? = ItemId(value)
        fun random(): ItemId = ItemId(Random.nextLong())
    }

    override fun toString(): String = value.toString()
}