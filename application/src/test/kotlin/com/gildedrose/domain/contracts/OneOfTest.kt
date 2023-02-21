package com.gildedrose.domain.contracts

import com.gildedrose.domain.contracts.Expirable
import com.gildedrose.domain.contracts.Expired
import com.gildedrose.domain.contracts.OneOf.JustExpired
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.Validatable
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OneOfTest {
    @Nested
    inner class JustValidTest {
        private val v = object : Validatable {
            override val isValid: Boolean
                get() = true
        }

        @Test
        fun `should be a VO`() {
            assertEquals(JustValid(Valid(v)!!), JustValid(Valid(v)!!))
        }
    }

    @Nested
    inner class JustExpiredTest {
        private val e = object : Expirable {
            override val isExpired: Boolean
                get() = true

        }

        @Test
        fun `should be a VO`() {
            assertEquals(JustExpired(Expired(e)!!), JustExpired(Expired(e)!!))
        }
    }
}