package com.gildedrose.web.repositories

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.web.infra.repositories.StockRepository
import com.gildedrose.web.infra.repositories.StockRepositoryAdapter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class StockRepositoryAdapterTest(
    @Autowired stockRepository: StockRepository
) {

    private val validItem = ValidItem(
        N("Apple")!!,
        JustValid(Valid(ShelfLife.NOW)!!),
        StandardQuality.of(10)!!
    )

    private val stockRepositoryAdapter = StockRepositoryAdapter(stockRepository)

    @Test
    fun `should persist items`() {
        assertEquals(emptyList<ValidItem>(), stockRepositoryAdapter.findAll())
        stockRepositoryAdapter.save(validItem)
        assertEquals(listOf(validItem), stockRepositoryAdapter.findAll())
    }

}