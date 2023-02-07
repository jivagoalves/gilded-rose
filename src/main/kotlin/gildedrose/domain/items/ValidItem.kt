package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.Quality
import gildedrose.domain.contracts.Lifecycle
import gildedrose.domain.contracts.OneOf.JustValid
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.degradation.Degradation

data class ValidItem constructor(
    override val name: Name,
    val lifecycle: JustValid<Lifecycle>,
    override val quality: Quality,
    val degradation: Degradation = Degradation.STANDARD
) : Item(JustValid(Valid(lifecycle.value)!!)) {

    override fun toString(): String =
        super.toString()

    override fun degrade(): ValidItem =
        copy(quality = degradation.degrade(quality))

}
