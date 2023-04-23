package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
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

    @Nested
    @DisplayName("deleteById - when the item exists")
    inner class DeleteById {
        private lateinit var orangeEntry: StockEntry

        @BeforeEach
        fun beforeEach() {
            stockRepositoryAdapter.save(apple)
            orangeEntry = stockRepositoryAdapter.save(orange)
        }

        @Test
        fun `should delete the item by id`() {
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

        @Test
        fun `should return true`() {
            assertTrue(stockRepositoryAdapter.deleteById(orangeEntry.id))
        }
    }

    @Test
    fun `deleteById - should return false for items that don't exist`() {
        assertFalse(stockRepositoryAdapter.deleteById(ItemId.random()))
    }
}