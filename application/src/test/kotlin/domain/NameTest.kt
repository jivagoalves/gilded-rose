package domain

import gildedrose.domain.N
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NameTest {
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
}