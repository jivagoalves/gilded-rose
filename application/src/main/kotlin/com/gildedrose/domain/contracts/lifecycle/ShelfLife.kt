package com.gildedrose.domain.contracts.lifecycle

import java.time.LocalDate

data class ShelfLife(
    override val registeredOn: LocalDate,
    override val sellBy: LocalDate
) : Lifecycle {
    companion object {
        val NOW: ShelfLife = ShelfLife(LocalDate.now(), LocalDate.now())
    }
}