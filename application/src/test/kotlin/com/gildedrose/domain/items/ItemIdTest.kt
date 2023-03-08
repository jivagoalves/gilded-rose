package com.gildedrose.domain.items

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ItemIdTest {
    @Test
    fun `toString() - should return the id value`() {
        assertEquals("1", "${ItemId.of(1)}")
    }
}