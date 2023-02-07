package gildedrose.domain.contracts.aging

interface Ageable<T> {
    fun age(): T
}
