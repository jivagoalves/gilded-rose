package com.gildedrose.domain.contracts

sealed class OneOf<T>(val value: T) {
    class JustValid<V : Validatable>(value: Valid<V>) : OneOf<V>(value.value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as JustValid<*>
            if (value != other.value) return false
            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }
    }

    class JustExpired<E : Expirable>(value: Expired<E>) : OneOf<E>(value.value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as JustExpired<*>
            if (value != other.value) return false
            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }
    }
}