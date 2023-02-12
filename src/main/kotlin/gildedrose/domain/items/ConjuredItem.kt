package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.contracts.OneOf.JustValid
import gildedrose.domain.contracts.aging.Ageable
import gildedrose.domain.contracts.aging.Aging
import gildedrose.domain.contracts.lifecycle.Lifecycle
import gildedrose.domain.quality.Quality

fun conjuredItem(
    name: Name,
    lifecycle: JustValid<Lifecycle>,
    quality: Quality,
    aging: Aging = Aging.Standard
): ValidItem =
    ValidItem(name, lifecycle, quality, conjuredAging(aging))

private fun conjuredAging(aging: Aging): Aging =
    object : Aging {
        override fun age(ageable: Ageable): Quality =
            aging.age(ageable).let { newQuality ->
                aging.age(ageable.withQuality(newQuality))
            }
    }
