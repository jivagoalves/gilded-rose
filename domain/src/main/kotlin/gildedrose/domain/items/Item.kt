package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.contracts.OneOf
import gildedrose.domain.contracts.Qualifiable
import gildedrose.domain.contracts.aging.Ageable
import gildedrose.domain.contracts.lifecycle.Lifecycle

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
