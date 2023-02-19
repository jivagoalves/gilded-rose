package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.contracts.Expired
import gildedrose.domain.contracts.OneOf.JustExpired
import gildedrose.domain.contracts.aging.Ageable
import gildedrose.domain.contracts.lifecycle.Lifecycle
import gildedrose.domain.quality.Quality
import gildedrose.domain.contracts.aging.Aging.Expired as ExpiredAging

data class ExpiredItem(
    override val name: Name,
    val lifecycle: JustExpired<Lifecycle>,
    override val quality: Quality
) : Item(JustExpired(Expired(lifecycle.value)!!)) {
    private val aging = ExpiredAging

    override fun toString(): String =
        super.toString()

    override fun withQuality(quality: Quality): Ageable =
        copy(quality = quality)

    override fun age(): ExpiredItem =
        copy(quality = aging.age(this))

}
