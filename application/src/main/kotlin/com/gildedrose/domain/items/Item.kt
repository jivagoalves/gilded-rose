package com.gildedrose.domain.items

import com.gildedrose.domain.Name
import com.gildedrose.domain.contracts.OneOf
import com.gildedrose.domain.contracts.Qualifiable
import com.gildedrose.domain.contracts.aging.Ageable
import com.gildedrose.domain.contracts.lifecycle.Lifecycle

sealed class Item(
    private val lifecycle: OneOf<Lifecycle>
) : Lifecycle by lifecycle.value
    , Qualifiable
    , Ageable
{
    abstract val name: Name
    abstract fun age(): Item

    override fun toString(): String =
        "$name, ${sellIn}, $quality"
}
