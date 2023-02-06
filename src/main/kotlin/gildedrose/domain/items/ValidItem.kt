package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.Quality
import gildedrose.domain.ShelfLife
import gildedrose.domain.contracts.Lifecycle
import gildedrose.domain.contracts.Valid
import gildedrose.domain.contracts.degradation.Degradation
import gildedrose.domain.contracts.degradation.Degradation.STANDARD

@Suppress("DataClassPrivateConstructor")
data class ValidItem private constructor(
    override val name: Name,
    val lifecycle: Lifecycle,
    override val quality: Quality,
    val degradation: Degradation
) : Item(lifecycle) {

    init {
        require(lifecycle.isValid) {
            "Can't be expired"
        }
    }

    companion object {
        operator fun invoke(
            name: Name,
            shelfLife: ShelfLife,
            quality: Quality,
            degradation: Degradation = STANDARD
        ): ValidItem? =
            try {
                ValidItem(name, shelfLife, quality, degradation)
            } catch (_: IllegalArgumentException) {
                null
            }

        operator fun invoke(
            name: Name,
            shelfLife: ShelfLife,
            quality: Quality,
            degradation: Degradation = STANDARD,
            validLifecycle: Valid<Lifecycle> = Valid(ShelfLife.NOW)!!
        ): ValidItem? =
            try {
                ValidItem(name, shelfLife, quality, degradation)
            } catch (_: IllegalArgumentException) {
                null
            }
    }
    override fun toString(): String =
        super.toString()

    override fun degrade(): ValidItem =
        copy(quality = degradation.degrade(quality))

}
