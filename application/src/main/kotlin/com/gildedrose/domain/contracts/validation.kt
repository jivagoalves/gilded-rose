package com.gildedrose.domain.contracts

interface Validatable {
    val isValid: Boolean
}

@JvmInline
value class Valid<out T: Validatable> private constructor(val value: T) {
    companion object {
        operator fun <T: Validatable> invoke(value: T): Valid<T>? = when {
            value.isValid -> Valid(value)
            else -> null
        }
    }
}