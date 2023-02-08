package gildedrose.domain.contracts.aging

import gildedrose.domain.Quality

interface Aging {
    fun age(ageable: Ageable): Quality

    object NONE : Aging {
        override fun age(ageable: Ageable): Quality =
            ageable.quality
    }

    object STANDARD : Aging {
        override fun age(ageable: Ageable): Quality =
            (ageable.quality - 1) ?: Quality.ZERO
    }

    object EXPIRED : Aging {
        override fun age(ageable: Ageable): Quality =
            (ageable.quality - 2) ?: Quality.ZERO
    }

    object REFINEMENT : Aging {
        override fun age(ageable: Ageable): Quality =
            (ageable.quality + 1) ?: Quality.FIFTY

    }
}