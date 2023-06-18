package com.gildedrose.domain.contracts

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExpiredTest {
    class ExpirableDummy(override val isExpired: Boolean) : Expirable

    @Test
    fun `should kill mutation test`() {
        assertNotNull(Expired(ExpirableDummy(true)))
        assertNull(Expired(ExpirableDummy(false)))
    }
}