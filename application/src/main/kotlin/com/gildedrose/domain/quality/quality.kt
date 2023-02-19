package com.gildedrose.domain.quality

interface Quality {
    val value: Int
    operator fun minus(n: Int): Quality
    operator fun plus(n: Int): Quality
    fun toZero(): Quality
}

@JvmInline
value class Standard private constructor(override val value: Int) : Quality {
    init {
        require(value in 0..50) { "Must be in between 0 or 50" }
    }

    override fun toString(): String = "${value}q"

    override operator fun minus(n: Int): Standard = of(value - n) ?: ZERO

    override operator fun plus(n: Int): Standard = of(value + n) ?: FIFTY

    override fun toZero(): Quality = ZERO

    companion object {
        val ZERO: Standard = Standard(0)
        val FIFTY: Standard = Standard(50)

        fun of(value: Int): Standard? = try {
            Standard(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}

@JvmInline
value class Legendary private constructor(override val value: Int) : Quality {
    companion object {
        val ZERO: Legendary = Legendary(0)

        fun of(value: Int): Legendary = Legendary(value)
    }

    override fun toString(): String = "${value}q"

    override operator fun minus(n: Int): Legendary = this

    override operator fun plus(n: Int): Legendary = this

    override fun toZero(): Quality = ZERO

}