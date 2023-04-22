package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.usecases.StockEntry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class StockRepositoryAdapterTest(
    @Autowired stockRepository: StockRepository
) {

    private val apple = ValidItem(
        N("Apple")!!,
        JustValid(Valid(ShelfLife.NOW)!!),
        StandardQuality.of(10)!!
    )
    private val orange = ValidItem(
        N("Orange")!!,
        JustValid(Valid(ShelfLife.NOW)!!),
        StandardQuality.of(3)!!
    )


    private val stockRepositoryAdapter = StockRepositoryAdapter(stockRepository)

    @Test
    fun `save - should persist items`() {
        assertEquals(emptyList<StockEntry>(), stockRepositoryAdapter.findAll())
        stockRepositoryAdapter.save(apple)
        assertEquals(
            listOf(apple),
            stockRepositoryAdapter.findAll().map(StockEntry::item)
        )
    }

    @Test
    fun `delete - should delete item by id`() {
        stockRepositoryAdapter.save(apple)
        val orangeEntry = stockRepositoryAdapter.save(orange)
        assertEquals(
            setOf(apple, orange),
            stockRepositoryAdapter.findAll().map(StockEntry::item).toSet()
        )
        stockRepositoryAdapter.deleteById(orangeEntry.id)
        assertEquals(
            setOf(apple),
            stockRepositoryAdapter.findAll().map(StockEntry::item).toSet()
        )
    }

}