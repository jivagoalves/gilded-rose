package com.gildedrose.usecases

import com.gildedrose.domain.N
import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.aging.Aging
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import java.time.LocalDate

interface IGetStock {
    fun asOf(date: LocalDate): Stock
}

object GetStock : IGetStock {
    override fun asOf(date: LocalDate): Stock =
        Stock.of(
            listOf(
                ValidItem(
                    N("Lemon")!!,
                    JustValid(Valid(ShelfLife.NOW)!!),
                    Standard.of(9)!!,
                    Aging.Expired
                ),
            )
        )
}