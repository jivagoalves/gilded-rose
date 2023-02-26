package com.gildedrose.domain.items

import arrow.core.*

interface Quality {
    val value: Int
    operator fun minus(n: Int): Quality
    operator fun plus(n: Int): Quality
    fun toZero(): Quality
}

@JvmInline
value class StandardQuality private constructor(override val value: Int) : Quality {
    init {
        require(value in 0..50) { InvalidQuality.description }
    }

    override fun toString(): String = "${value}q"

    override operator fun minus(n: Int): StandardQuality = of(value - n) ?: ZERO

    override operator fun plus(n: Int): StandardQuality = of(value + n) ?: FIFTY

    override fun toZero(): Quality = ZERO

    companion object {
        val ZERO: StandardQuality = StandardQuality(0)
        val FIFTY: StandardQuality = StandardQuality(50)

        fun of(value: Int): StandardQuality? =
            validatedFrom(value).orNull()

        fun validatedFrom(value: Int): Validated<Nel<ValidationError>, StandardQuality> =
            try {
                StandardQuality(value).valid()
            } catch (_: IllegalArgumentException) {
                InvalidQuality.invalidNel()
            }
    }

    object InvalidQuality : ValidationError {
        override val description = "Quality must be between 0 and 50"

        override fun toString(): String = "InvalidQuality"
    }
}

@JvmInline
value class LegendaryQuality private constructor(override val value: Int) : Quality {
    companion object {
        val ZERO: LegendaryQuality = LegendaryQuality(0)

        fun of(value: Int): LegendaryQuality = LegendaryQuality(value)
    }

    override fun toString(): String = "${value}q"

    override operator fun minus(n: Int): LegendaryQuality = this

    override operator fun plus(n: Int): LegendaryQuality = this

    override fun toZero(): Quality = ZERO

}