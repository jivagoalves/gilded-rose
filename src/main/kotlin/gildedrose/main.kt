package gildedrose

import gildedrose.domain.N
import gildedrose.domain.Quality
import gildedrose.domain.ShelfLife
import gildedrose.domain.contracts.Expired
import gildedrose.domain.contracts.OneOf.JustExpired
import gildedrose.domain.contracts.OneOf.JustValid
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.aging.Aging
import gildedrose.domain.items.ExpiredItem
import gildedrose.domain.items.Item
import gildedrose.domain.items.ValidItem
import java.time.LocalDate

fun main() {
    val jan1st = LocalDate.parse("2023-01-01")
    val jan31st = LocalDate.parse("2023-01-31")
    val validShelfLife = Valid(ShelfLife(jan1st, jan31st))!!
    val expiredShelfLife = Expired(ShelfLife(jan1st + 1.day, jan1st))!!

    ValidItem(N("Lemon"), JustValid(validShelfLife), Quality.Standard.of(9)!!, Aging.EXPIRED)

    val items = listOf(
        ValidItem(N("Orange"), JustValid(validShelfLife), Quality.Standard.of(9)!!),
        ValidItem(N("Lemon"), JustValid(validShelfLife), Quality.Standard.of(9)!!, Aging.EXPIRED),
        ValidItem(N("Sulfuras"), JustValid(validShelfLife), Quality.Legendary.of(80), Aging.NONE),
        ValidItem(N("Aged Brie"), JustValid(validShelfLife), Quality.Standard.of(42)!!, Aging.REFINEMENT),
        ExpiredItem(N("Apple"), JustExpired(expiredShelfLife), Quality.ZERO)
    )

    generateSequence(items) { it.map(Item::age) }
        .take(11)
        .forEachIndexed { day, degradedItems ->
            println("== Day $day == ")
            println("")
            degradedItems.forEach(::println)
            println("")
        }
}