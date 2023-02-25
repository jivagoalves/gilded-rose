package com.gildedrose.domain.contracts

sealed class OneOf<T>(val value: T) {
    class JustValid<V: Validatable>(value: Valid<V>): OneOf<V>(value.value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return value == (other as JustValid<*>).value
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
    class JustExpired<E: Expirable>(value: Expired<E>): OneOf<E>(value.value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return value == (other as JustExpired<*>).value
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
}