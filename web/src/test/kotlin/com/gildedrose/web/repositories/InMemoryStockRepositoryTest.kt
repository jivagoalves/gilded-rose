package com.gildedrose.web.repositories

import com.gildedrose.domain.N
import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InMemoryStockRepositoryTest {
    private val validItem = ValidItem(
        N("Orange")!!,
        OneOf.JustValid(Valid(ShelfLife.NOW)!!),
        Standard.of(9)!!
    )

    private val repo = InMemoryStockRepository()

    @Test
    fun `should persist the stock`() {
        assertEquals(emptyList<ValidItem>(), repo.findAll())
        repo.save(validItem)
        assertEquals(listOf(validItem), repo.findAll())
    }
}