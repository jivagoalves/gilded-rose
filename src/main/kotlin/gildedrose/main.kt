package gildedrose

import gildedrose.domain.N
import gildedrose.domain.Quality
import gildedrose.domain.ShelfLife
import gildedrose.domain.contracts.Expired
import gildedrose.domain.contracts.OneOf.JustExpired
import gildedrose.domain.contracts.OneOf.JustValid
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.degradation.Degradation
import gildedrose.domain.items.ExpiredItem
import gildedrose.domain.items.Item
import gildedrose.domain.items.ValidItem
import java.time.LocalDate

fun main() {
    val jan1st = LocalDate.parse("2023-01-01")
    val jan31st = LocalDate.parse("2023-01-31")
    val validShelfLife = Valid(ShelfLife(jan1st, jan31st))!!
    val expiredShelfLife = Expired(ShelfLife(jan1st + 1.day, jan1st))!!

    ValidItem(N("Lemon"), JustValid(validShelfLife), Quality.of(9)!!, Degradation.EXPIRED)

    val items = listOf(
        ValidItem(N("Orange"), JustValid(validShelfLife), Quality.of(9)!!),
        ValidItem(N("Lemon"), JustValid(validShelfLife), Quality.of(9)!!, Degradation.EXPIRED),
        ValidItem(N("Sulfuras"), JustValid(validShelfLife), Quality.of(80)!!, Degradation.NONE),
        ExpiredItem(N("Apple"), JustExpired(expiredShelfLife), Quality.ZERO)
    )

    generateSequence(items) { it.map(Item::degrade) }
        .take(11)
        .forEachIndexed { day, degradedItems ->
            println("== Day $day == ")
            println("")
            degradedItems.forEach(::println)
            println("")
        }
}