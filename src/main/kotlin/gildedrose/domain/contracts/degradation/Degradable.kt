package gildedrose.domain.contracts.degradation

interface Degradable<T> {
    fun degrade(): T
}
