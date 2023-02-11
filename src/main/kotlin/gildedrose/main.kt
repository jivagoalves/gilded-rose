package gildedrose

import gildedrose.domain.N
import gildedrose.domain.Quality
import gildedrose.domain.Stock
import gildedrose.domain.contracts.Expired
import gildedrose.domain.contracts.OneOf.JustExpired
import gildedrose.domain.contracts.OneOf.JustValid
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.aging.Aging
import gildedrose.domain.contracts.lifecycle.LegendaryLife
import gildedrose.domain.contracts.lifecycle.ShelfLife
import gildedrose.domain.items.ExpiredItem
import gildedrose.domain.items.ValidItem
import java.time.LocalDate

fun main() {
    val jan1st = LocalDate.parse("2023-01-01")
    val jan31st = LocalDate.parse("2023-01-31")
    val validShelfLife = Valid(ShelfLife(jan1st, jan31st))!!
    val validLegendaryLife = Valid(LegendaryLife(jan1st))!!
    val expiredShelfLife = Expired(ShelfLife(jan1st + 1.day, jan1st))!!

    ValidItem(N("Lemon")!!, JustValid(validShelfLife), Quality.Standard.of(9)!!, Aging.Expired)

    val stock = Stock.of(
        listOf(
            ValidItem(N("Orange")!!, JustValid(validShelfLife), Quality.Standard.of(9)!!),
            ValidItem(N("Lemon")!!, JustValid(validShelfLife), Quality.Standard.of(9)!!, Aging.Expired),
            ValidItem(N("Sulfuras")!!, JustValid(validLegendaryLife), Quality.Legendary.of(80), Aging.None),
            ValidItem(N("Aged Brie")!!, JustValid(validShelfLife), Quality.Standard.of(42)!!, Aging.Improvement),
            ValidItem(N("Pass")!!, JustValid(validShelfLife), Quality.Standard.of(42)!!, Aging.Improvement),
            ExpiredItem(N("Apple")!!, JustExpired(expiredShelfLife), Quality.ZERO)
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