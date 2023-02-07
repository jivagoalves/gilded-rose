package gildedrose.domain.contracts.degradation

import gildedrose.domain.Quality

interface Aging {
    fun age(quality: Quality): Quality

    object NONE : Aging {
        override fun age(quality: Quality): Quality =
            quality
    }

    object STANDARD : Aging {
        override fun age(quality: Quality): Quality =
            (quality - 1) ?: Quality.ZERO
    }

    object EXPIRED : Aging {
        override fun age(quality: Quality): Quality =
            (quality - 2) ?: Quality.ZERO
    }
}