package gildedrose.domain

sealed class Quality private constructor(open val value: Int){
    abstract operator fun minus(n: Int): Quality?

    companion object {
        val ZERO: Standard = Standard(0)
        val FIFTY: Standard = Standard(50)
    }

    data class Standard(override val value: Int) : Quality(value) {
        init {
            require(value in 0..50) { "Must be in between 0 or 50" }
        }

        override fun toString(): String = "${value}q"

        override operator fun minus(n: Int): Standard? = of(value - n)

        operator fun plus(n: Int): Standard? = of(value + n)

        companion object {
            fun of(value: Int): Standard? = try {
                Standard(value)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }
    data class Legendary(override val value: Int) : Quality(value) {
        companion object {
            fun of(value: Int): Legendary = Legendary(value)
        }

        override fun toString(): String = "${value}q"

        override operator fun minus(n: Int): Legendary = this

        operator fun plus(
            @Suppress("UNUSED_PARAMETER")
            n: Int
        ): Legendary = this

    }

}