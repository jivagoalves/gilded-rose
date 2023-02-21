package com.gildedrose.usecases

import com.gildedrose.domain.N
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import com.gildedrose.repositories.FakeStockRepository
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AddItemToStockTest {
    private val validItem = ValidItem(
        N("Orange")!!,
        JustValid(Valid(ShelfLife.NOW)!!),
        Standard.of(9)!!
    )

    private val fakeStockRepo = FakeStockRepository()

    @Test
    fun `should add items to the stock repository`() {
        assertEquals(emptyList(), fakeStockRepo.findAll())
        AddItemToStock(fakeStockRepo).addItem(validItem)
        assertEquals(listOf(validItem), fakeStockRepo.findAll())
    }
}