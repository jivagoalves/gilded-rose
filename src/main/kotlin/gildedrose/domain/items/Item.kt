package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.contracts.Lifecycle
import gildedrose.domain.contracts.Qualifiable
import gildedrose.domain.contracts.degradation.Degradable

sealed class Item(
    private val lifecycle: Lifecycle
) : Lifecycle by lifecycle
    , Qualifiable
    , Degradable<Item>
{
    abstract val name: Name

    override fun toString(): String =
        "$name, ${lifecycle.sellIn}, $quality"
}
