package com.gildedrose.domain.contracts

import com.gildedrose.domain.contracts.OneOf.JustExpired
import com.gildedrose.domain.contracts.OneOf.JustValid
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OneOfTest {
    @Nested
    inner class JustValidTest {
        private inner class V(val value: String) : Validatable {
            override val isValid: Boolean
                get() = true
        }

        private val v1 = V("v1")
        private val v2 = V("v2")

        @Test
        fun `should be a VO`() {
            assertEquals(JustValid(Valid(v1)!!), JustValid(Valid(v1)!!))
            assertEquals(JustValid(Valid(v2)!!), JustValid(Valid(v2)!!))
            assertNotEquals(JustValid(Valid(v1)!!), JustValid(Valid(v2)!!))
        }
    }

    @Nested
    inner class JustExpiredTest {
        private inner class E(val value: String) : Expirable {
            override val isExpired: Boolean
                get() = true
        }

        private val e1 = E("v1")
        private val e2 = E("v2")

        @Test
        fun `should be a VO`() {
            assertEquals(JustExpired(Expired(e1)!!), JustExpired(Expired(e1)!!))
            assertEquals(JustExpired(Expired(e2)!!), JustExpired(Expired(e2)!!))
            assertNotEquals(JustExpired(Expired(e1)!!), JustExpired(Expired(e2)!!))
        }
    }
}