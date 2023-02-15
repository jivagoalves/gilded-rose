package gildedrose.domain

@Suppress("FunctionName")
fun N(s: String): Name? = Name.of(s)

@JvmInline
value class Name private constructor(private val value: String) {
    init {
        require(value.isNotBlank()) { "Name can't be blank" }
    }

    companion object {
        fun of(value: String): Name? =
            try {
                Name(value)
            } catch (_: IllegalArgumentException) {
                null
            }
    }

    override fun toString(): String = value
}