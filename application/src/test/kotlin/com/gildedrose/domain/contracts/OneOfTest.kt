package com.gildedrose.domain.contracts

import com.gildedrose.domain.contracts.OneOf.JustExpired
import com.gildedrose.domain.contracts.OneOf.JustValid
import org.junit.jupiter.api.DisplayName
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

        @Nested
        @DisplayName("VO behaviour")
        inner class ValueObjectTest {
            @Test
            fun `should be equal based on its value`() {
                assertEquals(JustValid(Valid(v1)!!), JustValid(Valid(v1)!!))
                assertEquals(JustValid(Valid(v2)!!), JustValid(Valid(v2)!!))
                assertNotEquals(JustValid(Valid(v1)!!), JustValid(Valid(v2)!!))
            }

            @Test
            fun `should have same hash code based on its value`() {
                assertEquals(JustValid(Valid(v1)!!).hashCode(), JustValid(Valid(v1)!!).hashCode())
                assertNotEquals(JustValid(Valid(v1)!!).hashCode(), JustValid(Valid(v2)!!).hashCode())
            }
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

        @Nested
        @DisplayName("VO behaviour")
        inner class ValueObjectTest {
            @Test
            fun `should be equal based on its value`() {
                assertEquals(JustExpired(Expired(e1)!!), JustExpired(Expired(e1)!!))
                assertEquals(JustExpired(Expired(e2)!!), JustExpired(Expired(e2)!!))
                assertNotEquals(JustExpired(Expired(e1)!!), JustExpired(Expired(e2)!!))
            }

            @Test
            fun `should have same hash code based on its value`() {
                assertEquals(JustExpired(Expired(e1)!!).hashCode(), JustExpired(Expired(e1)!!).hashCode())
                assertNotEquals(JustExpired(Expired(e1)!!).hashCode(), JustExpired(Expired(e2)!!).hashCode())
            }
        }
    }
}