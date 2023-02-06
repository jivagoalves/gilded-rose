package gildedrose.domain

@Suppress("FunctionName")
fun N(s: String): Name = Name.of(s)

@JvmInline
value class Name private constructor(private val value: String) {
    companion object {
        fun of(value: String): Name = Name(value)
    }

    override fun toString(): String = value
}