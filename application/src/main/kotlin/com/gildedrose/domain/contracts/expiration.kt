package com.gildedrose.domain.contracts

interface Expirable {
    val isExpired: Boolean
}

@JvmInline
value class Expired<out T : Expirable> private constructor(val value: T) {
    companion object {
        operator fun <T : Expirable> invoke(value: T): Expired<T>? = when {
            value.isExpired -> Expired(value)
            else -> null
        }
    }
}
