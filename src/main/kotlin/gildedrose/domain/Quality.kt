package gildedrose.domain

@JvmInline
value class Quality private constructor(private val value: Int): Comparable<Quality> {
    init {
        require(value in 0..50 || value == 80) { "Must be in between 0 or 50 or exceptionally 80" }
    }

    companion object {
        val ZERO = Quality(0)
        val FIFTY = Quality(50)

        fun of(value: Int): Quality? =
            try {
                Quality(value)
            } catch (_: IllegalArgumentException) {
                null
            }
    }

    override operator fun compareTo(other: Quality): Int = value.compareTo(other.value)

    operator fun minus(n: Int): Quality? = of(value - n)

    operator fun plus(n: Int): Quality? = of(value + n)

    override fun toString(): String = "${value}q"
}

