package com.gildedrose.domain.items

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ItemIdTest {
    @Test
    fun `random() - should return ItemId with random values`() {
        assertNotEquals(ItemId.random(), ItemId.random())
    }

    @Test
    fun `toString() - should return the id value`() {
        assertEquals("1", "${ItemId.of(1)}")
    }
}