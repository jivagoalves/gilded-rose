package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.Quality
import gildedrose.domain.contracts.lifecycle.Lifecycle
import gildedrose.domain.contracts.OneOf.JustValid
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.aging.Aging

data class ValidItem constructor(
    override val name: Name,
    val lifecycle: JustValid<Lifecycle>,
    override val quality: Quality,
    val aging: Aging = Aging.STANDARD
) : Item(JustValid(Valid(lifecycle.value)!!)) {

    override fun toString(): String =
        super.toString()

    override fun age(): ValidItem =
        copy(quality = aging.age(quality))

}
