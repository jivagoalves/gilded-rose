package com.gildedrose.domain

import com.gildedrose.domain.items.Item

@JvmInline
value class Stock private constructor(private val items: List<Item>): List<Item> by items {
    fun age(): Stock =
        Stock(items.map(Item::age))

    companion object {
        val EMPTY: Stock = Stock(emptyList())

        fun of(items: List<Item>): Stock = Stock(items)
    }

}
