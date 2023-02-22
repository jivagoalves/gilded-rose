package com.gildedrose.domain

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class StockTest {
    @Nested
    @DisplayName("when there are no items")
    inner class WhenListOfItemsIsEmpty {
        private val stock = Stock.of(emptyList())

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
        private val item = ValidItem(N("Apple")!!, lifecycle, Standard.FIFTY)
        private val stock = Stock.of(listOf(item))

        @Test
        fun `should have size different from zero`() {
            assertEquals(1, stock.size)
        }

        @Test
        fun `should age its items`() {
            assertEquals(
                Stock.of(listOf(
                    item.age()
                )),
                stock.age()
            )
        }
    }

    private val jan1st: LocalDate = LocalDate.parse("2023-01-01")
    private val jan5th: LocalDate = LocalDate.parse("2023-01-05")
    private val lifecycle: JustValid<Lifecycle> = JustValid(Valid(ShelfLife(jan1st, jan5th))!!)
    private val item = ValidItem(N("Apple")!!, lifecycle, Standard.FIFTY)
    private val stock = Stock.of(listOf(item))

    @Test
    fun `should age according to the current date`() {
        assertEquals(stock, stock.asOf(jan1st))
        assertEquals(stock.age(), stock.asOf(jan1st.plusDays(1)))
    }
}
