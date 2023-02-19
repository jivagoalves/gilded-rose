package com.gildedrose.domain.contracts

sealed class OneOf<T>(val value: T) {
    class JustValid<V: Validatable>(value: Valid<V>): OneOf<V>(value.value)
    class JustExpired<E: Expirable>(value: Expired<E>): OneOf<E>(value.value)
}