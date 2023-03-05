package com.gildedrose

import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class SimulationTest {
    private val out = ByteArrayOutputStream()

    @Test
    fun `should output the stock state across the iterations`() {
        Simulation(STOCK, out).run(ITERATIONS)

        val expected = """
            == Day 0 ==

            Orange, 31d, 9q
            Conjured Orange, 31d, 9q
            Lemon, 31d, 9q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 42q
            Pass, 31d, 42q
            Apple, 0s, 13q

            == Day 1 ==

            Orange, 31d, 8q
            Conjured Orange, 31d, 7q
            Lemon, 31d, 7q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 43q
            Pass, 31d, 43q
            Apple, 0s, 11q

            == Day 2 ==

            Orange, 31d, 7q
            Conjured Orange, 31d, 5q
            Lemon, 31d, 5q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 44q
            Pass, 31d, 44q
            Apple, 0s, 9q

            == Day 3 ==

            Orange, 31d, 6q
            Conjured Orange, 31d, 3q
            Lemon, 31d, 3q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 45q
            Pass, 31d, 45q
            Apple, 0s, 7q

            == Day 4 ==

            Orange, 31d, 5q
            Conjured Orange, 31d, 1q
            Lemon, 31d, 1q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 46q
            Pass, 31d, 46q
            Apple, 0s, 5q

            == Day 5 ==

            Orange, 31d, 4q
            Conjured Orange, 31d, 0q
            Lemon, 31d, 0q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 47q
            Pass, 31d, 47q
            Apple, 0s, 3q

            == Day 6 ==

            Orange, 31d, 3q
            Conjured Orange, 31d, 0q
            Lemon, 31d, 0q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 48q
            Pass, 31d, 48q
            Apple, 0s, 1q

            == Day 7 ==

            Orange, 31d, 2q
            Conjured Orange, 31d, 0q
            Lemon, 31d, 0q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 49q
            Pass, 31d, 49q
            Apple, 0s, 0q

            == Day 8 ==

            Orange, 31d, 1q
            Conjured Orange, 31d, 0q
            Lemon, 31d, 0q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 50q
            Pass, 31d, 50q
            Apple, 0s, 0q

            == Day 9 ==

            Orange, 31d, 0q
            Conjured Orange, 31d, 0q
            Lemon, 31d, 0q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 50q
            Pass, 31d, 50q
            Apple, 0s, 0q

            == Day 10 ==

            Orange, 31d, 0q
            Conjured Orange, 31d, 0q
            Lemon, 31d, 0q
            Sulfuras, Infinity, 80q
            Aged Brie, 31d, 50q
            Pass, 31d, 50q
            Apple, 0s, 0q


        """.trimIndent()

        assertEquals(expected, out.toString())
    }
}