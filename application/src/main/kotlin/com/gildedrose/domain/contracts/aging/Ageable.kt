package com.gildedrose.domain.contracts.aging

import com.gildedrose.domain.contracts.Qualifiable
import com.gildedrose.domain.contracts.lifecycle.Lifecycle
import com.gildedrose.domain.items.Quality

interface Ageable : Lifecycle, Qualifiable {
    fun withQuality(quality: Quality): Ageable
}
