package com.gildedrose.domain.items

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.aging.Aging
import com.gildedrose.domain.contracts.lifecycle.Lifecycle

fun conjuredItem(
    name: Name,
    lifecycle: JustValid<Lifecycle>,
    quality: Quality,
    aging: Aging = Aging.Standard
): ValidItem =
    ValidItem(name, lifecycle, quality, conjuredAging(aging))

private fun conjuredAging(aging: Aging): Aging =
    Aging {
        aging.age(it).let { newQuality ->
            aging.age(it.withQuality(newQuality))
        }
    }
