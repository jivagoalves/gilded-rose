package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.Quality
import gildedrose.domain.contracts.Expired
import gildedrose.domain.contracts.Lifecycle
import gildedrose.domain.contracts.OneOf.JustExpired
import gildedrose.domain.contracts.aging.Aging.EXPIRED

data class ExpiredItem constructor(
    override val name: Name,
    val lifecycle: JustExpired<Lifecycle>,
    override val quality: Quality
) : Item(JustExpired(Expired(lifecycle.value)!!)) {
    private val degradation = EXPIRED

    override fun toString(): String =
        super.toString()

    override fun age(): ExpiredItem =
        copy(quality = degradation.age(quality))

}
