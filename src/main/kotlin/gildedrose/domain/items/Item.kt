package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.contracts.Lifecycle
import gildedrose.domain.contracts.OneOf
import gildedrose.domain.contracts.Qualifiable
import gildedrose.domain.contracts.degradation.Degradable

sealed class NQuality private constructor(open val value: Int){
    companion object {
        val ZERO: Standard = Standard(0)
        val FIFTY: Standard = Standard(50)
    }

    data class Standard(override val value: Int) : NQuality(value) {
        init {
            require(value in 0..50) { "Must be in between 0 or 50" }
        }

        operator fun minus(n: Int): Standard? = of(value - n)

        operator fun plus(n: Int): Standard? = of(value + n)

        companion object {
            fun of(value: Int): Standard? = try {
                Standard(value)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }
    data class Legendary(override val value: Int) : NQuality(value) {
        companion object {
            fun of(value: Int): Legendary = Legendary(value)
        }

        operator fun minus(n: Int): Legendary = this

        operator fun plus(n: Int): Legendary = this

    }

}
sealed class Item(
    private val lifecycle: OneOf<Lifecycle>
) : Lifecycle by lifecycle.value
    , Qualifiable
    , Degradable<Item>
{
    abstract val name: Name

    override fun toString(): String =
        "$name, ${sellIn}, $quality"
}
