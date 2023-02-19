package com.gildedrose.domain.items

import com.gildedrose.domain.Name
import com.gildedrose.domain.contracts.Expired
import com.gildedrose.domain.contracts.OneOf.JustExpired
import com.gildedrose.domain.contracts.aging.Ageable
import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import com.gildedrose.domain.quality.Quality
import com.gildedrose.domain.contracts.aging.Aging.Expired as ExpiredAging

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
