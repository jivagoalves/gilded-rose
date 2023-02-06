package gildedrose

import gildedrose.domain.N
import gildedrose.domain.Quality
import gildedrose.domain.ShelfLife
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.degradation.Degradation
import gildedrose.domain.items.ExpiredItem
import gildedrose.domain.items.Item
import gildedrose.domain.items.ValidItem
import java.time.LocalDate

fun main() {
    val jan1st = LocalDate.parse("2023-01-01")
    val jan31st = LocalDate.parse("2023-01-31")
    val janShelfLife = ShelfLife(jan1st, jan31st)
    val expiredShelfLife = ShelfLife(jan1st + 1.day, jan1st)
    val invalidExpiredShelfLife = Valid(expiredShelfLife)

    // BOOM!
    invalidExpiredShelfLife?.let {
        ValidItem(N("Lemon"), janShelfLife, Quality.of(9)!!, Degradation.EXPIRED, invalidExpiredShelfLife)
    }

    val items = listOf(
        ValidItem(N("Orange"), janShelfLife, Quality.of(9)!!)!!,
        ValidItem(N("Lemon"), janShelfLife, Quality.of(9)!!, Degradation.EXPIRED)!!,
        ValidItem(N("Sulfuras"), janShelfLife, Quality.of(80)!!, Degradation.NONE)!!,
        ExpiredItem(N("Apple"), expiredShelfLife, Quality.ZERO)!!
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