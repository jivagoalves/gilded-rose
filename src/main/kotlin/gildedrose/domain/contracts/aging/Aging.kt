package gildedrose.domain.contracts.aging

import gildedrose.domain.quality.Quality
import kotlin.time.Duration.Companion.days
import gildedrose.domain.quality.Standard as StandardQuality

interface Aging {
    fun age(ageable: Ageable): Quality

    object None : Aging {
        override fun age(ageable: Ageable): Quality =
            ageable.quality
    }

    object Standard : Aging {
        override fun age(ageable: Ageable): Quality =
            ageable.quality - 1
    }

    object Expired : Aging {
        override fun age(ageable: Ageable): Quality =
            ageable.quality - 2
    }

    object Improvement : Aging {
        override fun age(ageable: Ageable): Quality =
            ageable.quality + 1

    }

    object TimedImprovement {
        fun age(ageable: Ageable): Quality =
            when {
                ageable.sellIn < 0.days -> StandardQuality.ZERO
                ageable.sellIn <= 5.days -> ageable.quality + 3
                ageable.sellIn <= 10.days -> ageable.quality + 2
                else -> ageable.quality + 1
            }


    }
}