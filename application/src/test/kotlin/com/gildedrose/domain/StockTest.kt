package com.gildedrose.domain

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class StockTest {
    @Nested
    @DisplayName("when there are no items")
    inner class WhenListOfItemsIsEmpty {
        private val stock = Stock.EMPTY

        @Test
        fun `should have size zero`() {
            assertEquals(0, stock.size)
        }

        @Test
        fun `should be kept empty after aging`() {
            assertEquals(Stock.EMPTY, stock.age())
        }
    }

    @Nested
    @DisplayName("when there are items")
    inner class WhenListOfItemsIsNotEmpty {
        private val lifecycle: JustValid<Lifecycle> = JustValid(Valid(ShelfLife.NOW)!!)
        private val item = ValidItem(N("Apple")!!, lifecycle, StandardQuality.FIFTY)
        private val entry = StockEntry(ItemId.random(), item)
        private val stock = Stock.of(listOf(entry))

        @Test
        fun `should have size different from zero`() {
            assertEquals(1, stock.size)
        }

        @Test
        fun `should age its items`() {
            assertEquals(
                Stock.of(
                    listOf(
                        entry.age()
                    )
                ),
                stock.age()
            )
        }
    }

    @Nested
    @DisplayName("::asOf")
    inner class AgingTheStockAsOfDateTest {
        private val jan1st: LocalDate = LocalDate.parse("2023-01-01")
        private val jan5th: LocalDate = LocalDate.parse("2023-01-05")
        private val lifecycle: JustValid<Lifecycle> = JustValid(Valid(ShelfLife(jan1st, jan5th))!!)
        private val item = ValidItem(N("Apple")!!, lifecycle, StandardQuality.FIFTY)
        private val entry = StockEntry(ItemId.random(), item)
        private val stock = Stock.of(listOf(entry))

        @Test
        fun `should age the stock as of given date`() {
            assertEquals(stock, stock.asOf(jan1st))
            assertEquals(stock.age(), stock.asOf(jan1st.plusDays(1)))
        }
    }
}

class StockEntryTest {
    private val lifecycle: JustValid<Lifecycle> = JustValid(Valid(ShelfLife.NOW)!!)
    private val apple = ValidItem(N("Apple")!!, lifecycle, StandardQuality.FIFTY)
    private val orange = ValidItem(N("Orange")!!, lifecycle, StandardQuality.FIFTY)
    private val entry = StockEntry(ItemId.random(), apple)
    private val now = LocalDate.now()

    @Test
    fun `should kill mutation test`() {
        assertEquals(
            1,
            StockEntry(ItemId.of(1), apple).id.value,
        )
        assertEquals(
            "Apple",
            StockEntry(ItemId.of(1), apple).item.name.value,
        )
    }


    @Test
    fun `should be a VO`() {
        assertEquals(
            StockEntry(ItemId.of(1), apple),
            StockEntry(ItemId.of(1), apple),
        )
        assertNotEquals(
            StockEntry(ItemId.of(1), apple),
            StockEntry(ItemId.of(2), apple),
        )
        assertNotEquals(
            StockEntry(ItemId.of(1), apple),
            StockEntry(ItemId.of(1), orange),
        )
    }

    @Test
    fun `should wrap item`() {
        assertEquals(apple.toString(), entry.toString())
        assertEquals(apple.age(), entry.age().item)
        assertEquals(apple.asOf(now), entry.asOf(now).item)
    }
}
