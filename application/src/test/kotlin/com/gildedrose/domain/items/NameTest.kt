package com.gildedrose.domain.items

import arrow.core.invalidNel
import com.gildedrose.domain.items.Name.BlankName
import com.gildedrose.domain.items.Name.TooLong
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NameTest {
    @Test
    fun `should be a VO`() {
        assertEquals(N("a")!!, N("a")!!)
    }

    @Nested
    @DisplayName("when name is not blank")
    inner class WhenNameIsNotBlank {
        @Test
        fun `should be valid`() {
            assertNotNull(N("a"))
        }

    }

    @Nested
    @DisplayName("when name is blank")
    inner class WhenNameIsBlank {
        @Test
        fun `should not be valid`() {
            assertNull(N(""))
        }
    }


    @Test
    fun `should not allow name longer than 255`() {
        assertEquals(N("a".repeat(255))!!, N("a".repeat(255))!!)
        assertNull(N("a".repeat(256)))
    }


    @Nested
    @DisplayName("::validatedFrom")
    inner class ValidationTest {
        @Test
        fun `should return blank name validation error`() {
            assertEquals(BlankName.invalidNel(), Name.validatedFrom(""))
        }

        @Test
        fun `should return too long validation error`() {
            assertEquals(TooLong.invalidNel(), Name.validatedFrom("a".repeat(256)))
        }
    }

    @Nested
    inner class BlankNameTest {
        @Test
        fun `should have a description and string`() {
            assertEquals("Name can't be blank", BlankName.description)
            assertEquals("BlankName", BlankName.toString())
        }
    }

    @Nested
    inner class TooLongTest {
        @Test
        fun `should have a description and string`() {
            assertEquals("Name is too long (> 255)", TooLong.description)
            assertEquals("TooLong", TooLong.toString())
        }
    }
}