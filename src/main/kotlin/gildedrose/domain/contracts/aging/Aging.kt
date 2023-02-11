package gildedrose.domain.contracts.aging

import gildedrose.domain.Quality
import kotlin.time.Duration.Companion.days

interface Aging {
    fun age(ageable: Ageable): Quality

    object None : Aging {
        override fun age(ageable: Ageable): Quality =
            ageable.quality
    }

    object Standard : Aging {
        override fun age(ageable: Ageable): Quality =
            (ageable.quality - 1) ?: Quality.ZERO
    }

    object Expired : Aging {
        override fun age(ageable: Ageable): Quality =
            (ageable.quality - 2) ?: Quality.ZERO
    }

    object Improvement : Aging {
        override fun age(ageable: Ageable): Quality =
            (ageable.quality + 1) ?: Quality.FIFTY

    }

    object TimedImprovement {
        fun age(ageable: Ageable): Quality =
            when {
                ageable.sellIn < 0.days -> Quality.ZERO
                ageable.sellIn <= 5.days -> (ageable.quality + 3) ?: Quality.FIFTY
                ageable.sellIn <= 10.days -> (ageable.quality + 2) ?: Quality.FIFTY
                else -> (ageable.quality + 1) ?: Quality.FIFTY
            }


    }
}