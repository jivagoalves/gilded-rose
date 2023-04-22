package com.gildedrose.domain

import com.gildedrose.usecases.StockEntry
import java.time.LocalDate

@JvmInline
value class Stock private constructor(
    private val entries: List<StockEntry>
) : List<StockEntry> by entries {
    fun age(): Stock =
        Stock(entries.map(StockEntry::age))

    fun asOf(date: LocalDate): Stock =
        of(map { it.asOf(date) })

    companion object {
        val EMPTY: Stock = Stock(emptyList())

        fun of(entries: List<StockEntry>): Stock = Stock(entries)
    }

}
