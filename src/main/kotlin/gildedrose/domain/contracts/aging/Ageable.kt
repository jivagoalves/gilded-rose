package gildedrose.domain.contracts.aging

import gildedrose.domain.contracts.Qualifiable
import gildedrose.domain.contracts.lifecycle.Lifecycle
import gildedrose.domain.quality.Quality

interface Ageable: Lifecycle, Qualifiable {
    fun withQuality(quality: Quality): Ageable
}
