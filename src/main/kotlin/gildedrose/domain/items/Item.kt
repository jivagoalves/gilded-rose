package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.contracts.lifecycle.Lifecycle
import gildedrose.domain.contracts.OneOf
import gildedrose.domain.contracts.Qualifiable
import gildedrose.domain.contracts.aging.Ageable

sealed class Item(
    private val lifecycle: OneOf<Lifecycle>
) : Lifecycle by lifecycle.value
    , Qualifiable
    , Ageable<Item>
{
    abstract val name: Name

    override fun toString(): String =
        "$name, ${sellIn}, $quality"
}
