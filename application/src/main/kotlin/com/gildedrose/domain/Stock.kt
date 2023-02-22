package com.gildedrose.domain

import com.gildedrose.domain.items.Item
import java.time.LocalDate

@JvmInline
value class Stock private constructor(private val items: List<Item>): List<Item> by items {
    fun age(): Stock =
        Stock(items.map(Item::age))

    fun asOf(date: LocalDate): Stock =
        of(map { it.asOf(date) })

    companion object {
        val EMPTY: Stock = Stock(emptyList())

        fun of(items: List<Item>): Stock = Stock(items)
    }

}
