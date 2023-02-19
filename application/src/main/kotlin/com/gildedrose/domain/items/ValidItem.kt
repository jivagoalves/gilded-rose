package com.gildedrose.domain.items

import com.gildedrose.domain.Name
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.aging.Ageable
import com.gildedrose.domain.contracts.aging.Aging
import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import com.gildedrose.domain.quality.Quality

data class ValidItem(
    override val name: Name,
    val lifecycle: JustValid<Lifecycle>,
    override val quality: Quality,
    val aging: Aging = Aging.Standard
) : Item(JustValid(Valid(lifecycle.value)!!)) {

    override fun toString(): String =
        super.toString()

    override fun withQuality(quality: Quality): Ageable =
        copy(quality = quality)

    override fun age(): ValidItem =
        copy(quality = aging.age(this))

}
