package gildedrose.domain.items

import gildedrose.domain.Name
import gildedrose.domain.Quality
import gildedrose.domain.ShelfLife
import gildedrose.domain.contracts.Lifecycle
import gildedrose.domain.contracts.degradation.Degradation.EXPIRED

@Suppress("DataClassPrivateConstructor")
data class ExpiredItem private constructor(
    override val name: Name,
    val lifecycle: Lifecycle,
    override val quality: Quality
) : Item(lifecycle) {
    private val degradation = EXPIRED

    init {
        require(lifecycle.isExpired) {
            "Can't be registered before the sell by date"
        }
    }

    companion object {
        operator fun invoke(name: Name, shelfLife: ShelfLife, quality: Quality): ExpiredItem? =
            try {
                ExpiredItem(name, shelfLife, quality)
            } catch (_: IllegalArgumentException) {
                null
            }
    }

    override fun toString(): String =
        super.toString()

    override fun degrade(): ExpiredItem =
        copy(quality = degradation.degrade(quality))

}
