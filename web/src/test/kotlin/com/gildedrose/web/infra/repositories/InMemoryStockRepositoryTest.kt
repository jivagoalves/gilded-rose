package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InMemoryStockRepositoryTest {
    private val validItem = ValidItem(
        N("Orange")!!,
        OneOf.JustValid(Valid(ShelfLife.NOW)!!),
        StandardQuality.of(9)!!
    )

    private val repo = InMemoryStockRepository()

    @Test
    fun `should persist the stock`() {
        assertEquals(emptyList<ValidItem>(), repo.findAll())
        repo.save(validItem)
        assertEquals(listOf(validItem), repo.findAll())
    }
}