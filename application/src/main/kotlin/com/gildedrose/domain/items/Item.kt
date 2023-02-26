package com.gildedrose.domain.items

import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.aging.Ageable
import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

sealed class Item(
    private val lifecycle: OneOf<Lifecycle>
) : Lifecycle by lifecycle.value, Ageable {
    abstract val name: Name
    abstract fun age(): Item

    override fun toString(): String =
        "$name, $sellIn, $quality"

    private val sellInAsOfDate by lazy { SellInAsOfDate(registeredOn, sellBy) }

    override val sellIn
        get() = sellInAsOfDate.sellIn

    fun asOf(date: LocalDate): Item =
        (1..registeredOn.until(date).days)
            .fold(this) { item, _ -> item.age() }
            .let { it.also { it.sellInAsOfDate.asOf(date) } }
}

private class SellInAsOfDate(private var date: LocalDate, private val sellBy: LocalDate) {
    val sellIn: Duration
        get() =
            durationInDaysBetween(date, sellBy)

    fun asOf(date: LocalDate) {
        this.date = date
    }

    private fun durationInDaysBetween(startDate: LocalDate, endDate: LocalDate): Duration =
        ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)).days

}
