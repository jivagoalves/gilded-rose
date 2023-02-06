package gildedrose.domain.contracts.degradation

import gildedrose.domain.Quality

interface Degradation {
    fun degrade(quality: Quality): Quality

    object NONE : Degradation {
        override fun degrade(quality: Quality): Quality =
            quality
    }

    object STANDARD : Degradation {
        override fun degrade(quality: Quality): Quality =
            (quality - 1) ?: Quality.ZERO
    }

    object EXPIRED : Degradation {
        override fun degrade(quality: Quality): Quality =
            (quality - 2) ?: Quality.ZERO
    }
}