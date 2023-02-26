package com.gildedrose

import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.Expired
import com.gildedrose.domain.contracts.OneOf.JustExpired
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.aging.Aging
import com.gildedrose.domain.contracts.lifecycle.LegendaryLife
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.*
import java.time.LocalDate

fun main() {
    val jan1st = LocalDate.parse("2023-01-01")
    val jan31st = LocalDate.parse("2023-01-31")
    val validShelfLife = Valid(ShelfLife(jan1st, jan31st))!!
    val validLegendaryLife = Valid(LegendaryLife(jan1st))!!
    val expiredShelfLife = Expired(ShelfLife(jan1st + 1.day, jan1st))!!

    ValidItem(N("Lemon")!!, JustValid(validShelfLife), StandardQuality.of(9)!!, Aging.Expired)

    val stock = Stock.of(
        listOf(
            ValidItem(N("Orange")!!, JustValid(validShelfLife), StandardQuality.of(9)!!),
            conjuredItem(N("Conjured Orange")!!, JustValid(validShelfLife), StandardQuality.of(9)!!),
            ValidItem(N("Lemon")!!, JustValid(validShelfLife), StandardQuality.of(9)!!, Aging.Expired),
            ValidItem(N("Sulfuras")!!, JustValid(validLegendaryLife), LegendaryQuality.of(80), Aging.None),
            ValidItem(N("Aged Brie")!!, JustValid(validShelfLife), StandardQuality.of(42)!!, Aging.Improvement),
            ValidItem(N("Pass")!!, JustValid(validShelfLife), StandardQuality.of(42)!!, Aging.TimedImprovement),
            ExpiredItem(N("Apple")!!, JustExpired(expiredShelfLife), StandardQuality.of(13)!!)
        )
    )

    generateSequence(stock, Stock::age)
        .take(11)
        .forEachIndexed { day, degradedItems ->
            println("== Day $day == ")
            println("")
            degradedItems.forEach(::println)
            println("")
        }
}